package com.example.orderprocessingconsumer.service;

import feign.RetryableException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "item-service", url = "http://localhost:8081/item")
public interface ItemServiceClient {
    @PostMapping("/try")
    ResponseEntity<String> tryLock(@RequestParam String requestTraceId,@RequestParam String listItemLock);
    @PostMapping("/confirm")
    ResponseEntity<String> confirmLock(@RequestParam String requestTraceId);
    @PostMapping("/cancel")
    ResponseEntity<String> cancelLock(@RequestParam String requestTraceId);
}
