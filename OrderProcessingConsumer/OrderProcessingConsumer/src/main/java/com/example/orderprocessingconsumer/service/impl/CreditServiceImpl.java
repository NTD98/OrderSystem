package com.example.orderprocessingconsumer.service.impl;

import com.example.orderprocessingconsumer.dto.OrderRequest;
import com.example.orderprocessingconsumer.exception.ApiGeneralException;
import com.example.orderprocessingconsumer.exception.RetryableException;
import com.example.orderprocessingconsumer.service.CreditService;
import com.example.orderprocessingconsumer.service.CreditServiceClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {
    public final CreditServiceClient creditServiceClient;

    @Override
    public void tryLock(OrderRequest orderRequest, BigDecimal totalRequiredLockAmount) {
        try {
            ResponseEntity<String> tryCreditResponse = creditServiceClient.tryLock(orderRequest.getCustomerId(), totalRequiredLockAmount);
            if (!tryCreditResponse.getStatusCode().is2xxSuccessful()) {

                throw new ApiGeneralException(tryCreditResponse.getBody(), tryCreditResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }

    @Override
    public void confirmLock(OrderRequest orderRequest) {
        try {
            ResponseEntity<String> confirmCreditResponse = creditServiceClient.confirmLock(orderRequest.getCustomerId());
            if (!confirmCreditResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiGeneralException(confirmCreditResponse.getBody(), confirmCreditResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }

    @Override
    public void cancelLock(OrderRequest orderRequest) {
        try {
            ResponseEntity<String> confirmCreditResponse = creditServiceClient.cancelLock(orderRequest.getCustomerId());
            if (!confirmCreditResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiGeneralException(confirmCreditResponse.getBody(), confirmCreditResponse.getStatusCode().value());
            }
        } catch (FeignException.GatewayTimeout ex) {
            throw new RetryableException();
        }
    }
}
