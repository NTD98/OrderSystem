package com.example.orderapi.service.impl;

import com.example.orderapi.exception.GeneralErrorException;
import com.example.orderapi.exception.InvalidRequestException;
import com.example.orderapi.dto.OrderRequest;
import com.example.orderapi.entity.ItemEntity;
import com.example.orderapi.entity.OrderEntity;
import com.example.orderapi.entity.OrderStatusEntity;
import com.example.orderapi.producer.KafkaProducer;
import com.example.orderapi.repository.OrderRepository;
import com.example.orderapi.service.OrderReservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderReservationImpl implements OrderReservation {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    @Override
    public String reservation(OrderRequest order) {
        var isExist = orderRepository.existsByTraceId(order.getTraceId());
        if (isExist)
        {
            throw new InvalidRequestException();
        }
        try {
            OrderEntity orderEntity = buildCreateOrder(order);
            orderRepository.save(orderEntity);
            kafkaProducer.sendMessage(objectMapper.writeValueAsString(order));
        } catch (Exception e) {
            throw new GeneralErrorException(e.getMessage());
        }
        return order.getTraceId();
    }

    @Override
    public String tryReservation(OrderRequest order) {
        OrderEntity orderEntity = buildCreateOrder(order);
        orderEntity.setStatuses(Set.of(OrderStatusEntity.builder()
                .status("TRY")
                .order(orderEntity)
                .build()));
        orderEntity.setStatus("TRY");
        orderRepository.save(orderEntity);
        return orderEntity.getTraceId();
    }

    @Override
    public String confirmReservation(OrderRequest order) {
        OrderEntity orderEntity = orderRepository.findById(order.getTraceId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (!orderEntity.getStatus().equals("TRY")) {
            throw new IllegalArgumentException("Order not in TRY status");
        }
        orderEntity.setStatuses(Set.of(OrderStatusEntity.builder()
                .status("CONFIRM")
                .order(orderEntity)
                .build()));
        orderEntity.setStatus("CONFIRM");
        orderRepository.save(orderEntity);
        return orderEntity.getTraceId();
    }

    @Override
    public String cancelReservation(OrderRequest order) {
        OrderEntity orderEntity = orderRepository.findById(order.getTraceId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (orderEntity.getStatus().equals("CONFIRM")) {
            throw new IllegalArgumentException("Order already confirmed");
        }
        orderEntity.setStatuses(Set.of(OrderStatusEntity.builder()
                .status("CANCELED")
                .order(orderEntity)
                .build()));
        orderEntity.setStatus("CANCELED");
        orderRepository.save(orderEntity);
        return orderEntity.getTraceId();
    }

    OrderEntity buildCreateOrder(OrderRequest order){
        OrderEntity orderEntity = OrderEntity.builder()
                .traceId(order.getTraceId())
                .customerId(order.getCustomerId())
                .build();

        // Build the ItemEntities and set their order reference
        Set<ItemEntity> items = order.getItems().stream()
                .map(item -> ItemEntity.builder()
                        .itemId(item.getItemId())
                        .quantity(item.getQuantity())
                        .order(orderEntity)
                        .build())
                .collect(Collectors.toSet());

        // Set the items to the order entity
        orderEntity.setItems(items);
        return orderEntity;
    }
}
