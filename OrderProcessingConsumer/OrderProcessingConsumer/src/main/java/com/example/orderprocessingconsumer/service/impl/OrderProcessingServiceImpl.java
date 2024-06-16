package com.example.orderprocessingconsumer.service.impl;

import com.example.orderprocessingconsumer.configuration.AppProperties;
import com.example.orderprocessingconsumer.dto.OrderRequest;
import com.example.orderprocessingconsumer.enums.OrderStatus;
import com.example.orderprocessingconsumer.producer.KafkaProducer;
import com.example.orderprocessingconsumer.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Log4j2
public class OrderProcessingServiceImpl implements OrderProcessingService {
    private final CreditService creditService;
    private final ItemService itemService;
    private final KafkaProducer kafkaProducer;
    private final OrderStatusService orderStatusService;
    private final AppProperties appProperties;

    @Override
    public void processingEvent(OrderRequest orderRequest) {
        try {
            // Step 1: Try to lock credit
            // call API to get current price of items, pretend to be 10 for simplicity
            var totalRequiredLockAmount = BigDecimal.TEN;

            creditService.tryLock(orderRequest, totalRequiredLockAmount);
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.CreditStatus.CREDIT_LOCK_ACQUIRED.name());
            //call API update status of OrderRequest to CREDIT_LOCK_ACQUIRED

            //Step 2: Try to lock item
            //call API try to lock item
            var listItemLock = orderRequest.getItems().stream()
                    .map(item -> item.getItemId() + ":" + item.getQuantity())
                    .toList();
            var listItemLockStr = String.join(",", listItemLock);

            itemService.tryLock(orderRequest, listItemLockStr);
            //call API update status of OrderRequest to ITEM_LOCK_ACQUIRED
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.ItemStatus.ITEM_LOCK_ACQUIRED.name());

            // Step 3: Confirm the credit lock
            creditService.confirmLock(orderRequest);
            //call API update status of OrderRequest to CREDIT_LOCK_CONFIRMED
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.CreditStatus.CREDIT_LOCK_CONFIRMED.name());

            // Step 4: Confirm the item lock
            itemService.confirmLock(orderRequest);
            //call API update status of OrderRequest to ITEM_LOCK_CONFIRMED
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.ItemStatus.ITEM_LOCK_CONFIRMED.name());

            kafkaProducer.sendMessage(appProperties.getKafka().getResponseTopic(), OrderStatus.ORDER_COMPLETED.name());
        } catch (Exception ex) {
            //release lock if one of those step is failed
            creditService.cancelLock(orderRequest);
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.CreditStatus.CREDIT_LOCK_CANCELLED.name());
            itemService.cancelLock(orderRequest);
            orderStatusService.updateStatus(orderRequest.getTraceId(), OrderStatus.ItemStatus.ITEM_LOCK_CANCELLED.name());
            log.error(ex.getMessage());
            //send to error-consumer topic
            kafkaProducer.sendMessage(appProperties.getKafka().getResponseTopic(), OrderStatus.ORDER_FAILED.name());
        }
    }
}
