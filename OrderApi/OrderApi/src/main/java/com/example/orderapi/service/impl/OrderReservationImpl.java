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

        Set<OrderStatusEntity> status = Set.of(OrderStatusEntity.builder()
                .status("ORDER_CREATED")
                .order(orderEntity)
                .build());

        // Set the items to the order entity
        orderEntity.setItems(items);
        orderEntity.setStatuses(status);
        return orderEntity;
    }
}
