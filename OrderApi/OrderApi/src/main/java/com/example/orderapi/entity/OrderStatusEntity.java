package com.example.orderapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class OrderStatusEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    private String status;
    private LocalDateTime createdDateTime;

    @PrePersist
    void initCreatedDate(){
        createdDateTime = LocalDateTime.now();
    }
}
