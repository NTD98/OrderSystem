package com.example.orderapi.repository;

import com.example.orderapi.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {
    boolean existsByTraceId(String orderTraceId);
}
