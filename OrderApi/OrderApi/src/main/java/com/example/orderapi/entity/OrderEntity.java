package com.example.orderapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderEntity {

    @Id
    private String traceId;
    private String customerId;
    private LocalDateTime createdDateTime;
    private String status;

    @PrePersist
    void initCreatedDate(){
        createdDateTime = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemEntity> items = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderStatusEntity> statuses = new HashSet<>();
}
