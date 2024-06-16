package com.example.orderprocessingconsumer.service;

import com.example.orderprocessingconsumer.dto.OrderRequest;

public interface OrderProcessingService {
    void processingEvent(OrderRequest orderRequest);
}
