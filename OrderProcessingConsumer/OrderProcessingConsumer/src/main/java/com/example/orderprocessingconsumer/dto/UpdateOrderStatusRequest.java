package com.example.orderprocessingconsumer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatusRequest {
    private String requestId;
    private String status;
}
