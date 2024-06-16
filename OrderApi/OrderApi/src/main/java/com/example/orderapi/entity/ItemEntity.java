package com.example.orderapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ItemEntity {
    @Id
    private String itemId;
    private String productName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private LocalDateTime createdDateTime;

    @PrePersist
    void initCreatedDate(){
        createdDateTime = LocalDateTime.now();
    }

    // Constructors, getters, and setters
}
