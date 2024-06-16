package com.example.orderprocessingconsumer.listener.impl;

import com.example.orderprocessingconsumer.dto.OrderRequest;
import com.example.orderprocessingconsumer.dto.UpdateOrderStatusRequest;
import com.example.orderprocessingconsumer.enums.OrderStatus;
import com.example.orderprocessingconsumer.listener.KafkaListenerService;
import com.example.orderprocessingconsumer.service.OrderProcessingService;
import com.example.orderprocessingconsumer.service.OrderStatusClient;
import com.example.orderprocessingconsumer.service.OrderStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class KafkaListenerServiceImpl implements KafkaListenerService {
    private final ObjectMapper objectMapper;
    private final OrderProcessingService orderProcessingService;
    private final OrderStatusService orderStatusService;
    @Override
    @KafkaListener(topics = "${app.kafka.customer-topic}", groupId = "order-processing-group")
    public void listenEvent(String eventMessage) {
        log.info("Receive message: {}",eventMessage);
        try {
            var request = objectMapper.readValue(eventMessage, OrderRequest.class);

            //Call api get current status of requestId
            var isValid = isRequestValid(request.getTraceId());
            if (isValid)
            {
                //call API update status of requestId become ORDER_PROCESSING
                orderStatusService.updateStatus(request.getTraceId(),OrderStatus.ORDER_PROCESSING.name());
                orderProcessingService.processingEvent(request);
            }
        } catch (Exception e) {
            log.error("Error: {}",eventMessage);
            //send event to ErrorConsumer to handle
        }
    }
    private boolean isRequestValid(String requestId){
        var statusResponse = orderStatusService.getCurrentStatus(requestId);
        return OrderStatus.ORDER_CREATED.name().equals(statusResponse);
    }
}
