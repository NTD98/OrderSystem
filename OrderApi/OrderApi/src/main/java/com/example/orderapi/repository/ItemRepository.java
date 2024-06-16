package com.example.orderapi.repository;

import com.example.orderapi.entity.ItemEntity;
import com.example.orderapi.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity, String> {
}
