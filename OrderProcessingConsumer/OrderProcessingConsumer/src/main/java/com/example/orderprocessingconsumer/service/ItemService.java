package com.example.orderprocessingconsumer.service;

import com.example.orderprocessingconsumer.dto.OrderRequest;
import com.example.orderprocessingconsumer.exception.RetryableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface ItemService {
    @Retryable(retryFor = {RetryableException.class},
            maxAttemptsExpression = "${app.api.retry.max-attempts}",
            backoff = @Backoff(delayExpression = "${app.api.retry.max-delay}"))
    void tryLock(OrderRequest orderRequest, String listItemLockStr);
    @Retryable(retryFor = {RetryableException.class},
            maxAttemptsExpression = "${app.api.retry.max-attempts}",
            backoff = @Backoff(delayExpression = "${app.api.retry.max-delay}"))
    void confirmLock(OrderRequest orderRequest);
    @Retryable(retryFor = {RetryableException.class},
            maxAttemptsExpression = "${app.api.retry.max-attempts}",
            backoff = @Backoff(delayExpression = "${app.api.retry.max-delay}"))
    void cancelLock(OrderRequest orderRequest);
}
