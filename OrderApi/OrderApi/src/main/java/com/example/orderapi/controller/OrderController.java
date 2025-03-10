package com.example.orderapi.controller;

import com.example.orderapi.dto.OrderRequest;
import com.example.orderapi.service.OrderReservation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.version}/order")
@AllArgsConstructor
@Validated
public class OrderController {
    private final OrderReservation orderReservation;
    @PostMapping
    public String sendMessageToKafkaTopic(@RequestBody @Valid OrderRequest message) {
        var traceId = orderReservation.reservation(message);
        return "Order sent to Kafka Topic with traceId: " + traceId;
    }
    @PostMapping("/try")
    public String tryReservation(@RequestBody @Valid OrderRequest message) {
        var traceId = orderReservation.tryReservation(message);
        return "Order try reservation with traceId: " + traceId;
    }

    @PostMapping("/confirm")
    public String confirmReservation(@RequestBody @Valid OrderRequest message) {
        var traceId = orderReservation.confirmReservation(message);
        return "Order confirmed with traceId: " + traceId;
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestBody @Valid OrderRequest message) {
        var traceId = orderReservation.cancelReservation(message);
        return "Order canceled with traceId: " + traceId;
    }
}
