package com.example.orderapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatusRequest {
    private String requestId;
    private String status;
}
