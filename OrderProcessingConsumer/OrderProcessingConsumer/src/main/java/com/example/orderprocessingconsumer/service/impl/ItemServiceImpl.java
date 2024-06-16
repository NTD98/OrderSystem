package com.example.orderprocessingconsumer.service.impl;

import com.example.orderprocessingconsumer.dto.OrderRequest;
import com.example.orderprocessingconsumer.exception.ApiGeneralException;
import com.example.orderprocessingconsumer.exception.RetryableException;
import com.example.orderprocessingconsumer.service.ItemService;
import com.example.orderprocessingconsumer.service.ItemServiceClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemServiceClient itemServiceClient;
    @Override
    public void tryLock(OrderRequest orderRequest, String listItemLockStr) {
        try {
            ResponseEntity<String> tryItemResponse = itemServiceClient.tryLock(orderRequest.getTraceId(), listItemLockStr);
            if (!tryItemResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiGeneralException(tryItemResponse.getBody(), tryItemResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }

    @Override
    public void confirmLock(OrderRequest orderRequest) {
        try {
            ResponseEntity<String> tryItemResponse = itemServiceClient.confirmLock(orderRequest.getTraceId());
            if (!tryItemResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiGeneralException(tryItemResponse.getBody(), tryItemResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }

    @Override
    public void cancelLock(OrderRequest orderRequest) {
        try {
            ResponseEntity<String> tryItemResponse = itemServiceClient.cancelLock(orderRequest.getTraceId());
            if (!tryItemResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiGeneralException(tryItemResponse.getBody(), tryItemResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }
}
