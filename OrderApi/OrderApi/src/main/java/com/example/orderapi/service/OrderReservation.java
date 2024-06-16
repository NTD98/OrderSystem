package com.example.orderapi.service;

import com.example.orderapi.dto.OrderRequest;

public interface OrderReservation {
    String reservation(OrderRequest order);
}
