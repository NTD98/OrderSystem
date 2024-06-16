package com.example.orderprocessingconsumer.service;
import com.example.orderprocessingconsumer.exception.RetryableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface OrderStatusService {
    @Retryable(retryFor = {RetryableException.class},
            maxAttemptsExpression = "${app.api.retry.max-attempts}",
            backoff = @Backoff(delayExpression = "${app.api.retry.max-delay}"))
    String getCurrentStatus(String requestTraceId);
    @Retryable(retryFor = {RetryableException.class},
            maxAttemptsExpression = "${app.api.retry.max-attempts}",
            backoff = @Backoff(delayExpression = "${app.api.retry.max-delay}"))
    void updateStatus(String requestId,String status);
}
