package com.example.orderprocessingconsumer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

@Data
public class OrderRequest implements Serializable{
    private String traceId;
    private String customerId;
    private Set<Item> items;
    @Data
    @EqualsAndHashCode
    public static class Item implements Serializable {
        private String itemId;
        @EqualsAndHashCode.Exclude
        private int quantity;
    }
}
