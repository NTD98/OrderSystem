package com.example.orderprocessingconsumer.service.impl;

import com.example.orderprocessingconsumer.dto.UpdateOrderStatusRequest;
import com.example.orderprocessingconsumer.exception.ApiGeneralException;
import com.example.orderprocessingconsumer.exception.RetryableException;
import com.example.orderprocessingconsumer.service.OrderStatusClient;
import com.example.orderprocessingconsumer.service.OrderStatusService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusClient orderStatusClient;
    @Override
    public String getCurrentStatus(String requestTraceId) {
        try {
            var statusResponse = orderStatusClient.getCurrentStatus(requestTraceId);
            if (statusResponse.getStatusCode().is2xxSuccessful()) {
                return statusResponse.getBody();
            }
            throw new RetryableException();
        }catch (FeignException.GatewayTimeout | RetryableException ex) {
            throw new RetryableException();
        }
    }

    @Override
    public void updateStatus(String requestId,String status) {
        try {
            orderStatusClient.updateStatus(
                    UpdateOrderStatusRequest.builder()
                            .requestId(requestId)
                            .status(status)
                            .build()
            );
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }
}
