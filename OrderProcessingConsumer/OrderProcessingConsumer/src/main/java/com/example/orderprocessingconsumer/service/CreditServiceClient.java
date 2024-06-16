package com.example.orderprocessingconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "credit-service", url = "http://localhost:8081/credit")
public interface CreditServiceClient {
    @PostMapping("/try")
    ResponseEntity<String> tryLock(@RequestParam String customerId, @RequestParam BigDecimal amount);
    @PostMapping("/confirm")
    ResponseEntity<String> confirmLock(@RequestParam String customerId);
    @PostMapping("/cancel")
    ResponseEntity<String> cancelLock(@RequestParam String customerId);
}