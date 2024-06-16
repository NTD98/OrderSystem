package com.example.orderprocessingconsumer.service;

import com.example.orderprocessingconsumer.dto.UpdateOrderStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-status-service", url = "http://localhost:8081/orderStatus")
public interface OrderStatusClient {
    @GetMapping
    ResponseEntity<String> getCurrentStatus(@RequestParam String requestTraceId);

    @PostMapping
    ResponseEntity<String> updateStatus(@RequestBody UpdateOrderStatusRequest request);
}
