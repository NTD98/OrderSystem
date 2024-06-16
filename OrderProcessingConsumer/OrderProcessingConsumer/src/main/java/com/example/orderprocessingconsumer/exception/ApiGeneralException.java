package com.example.orderprocessingconsumer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiGeneralException extends RuntimeException{
    private final String errorMessage;
    private final int errorStatusCode;
}
