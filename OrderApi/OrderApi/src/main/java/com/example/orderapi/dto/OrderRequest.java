package com.example.orderapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

@Data
public class OrderRequest implements Serializable{
    @NotBlank
    private String traceId;
    @NotBlank
    private String customerId;
    @NotNull
    @Valid
    private Set<Item> items;
    @Data
    @EqualsAndHashCode
    public static class Item implements Serializable {
        @NotBlank
        private String itemId;
        @Positive
        @EqualsAndHashCode.Exclude
        private int quantity;
    }
}
