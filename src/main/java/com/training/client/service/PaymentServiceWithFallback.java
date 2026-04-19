package com.training.client.service;

import com.training.client.dto.PaymentResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentServiceWithFallback {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceWithFallback.class);
    
    private final Map<String, PaymentResponseDto> pendingPayments = new ConcurrentHashMap<>();

    public PaymentResponseDto getFallbackPayment(String transactionId) {
        logger.warn("Returning fallback payment for transaction: {}", transactionId);
        if (pendingPayments.containsKey(transactionId)) {
            return pendingPayments.get(transactionId);
        }
        return createPendingPayment(transactionId);
    }

    public PaymentResponseDto createPendingFallback(String transactionId, String customerId, BigDecimal amount) {
        logger.warn("Creating pending fallback payment for: {}", transactionId);
        PaymentResponseDto payment = new PaymentResponseDto();
        payment.setTransactionId(transactionId);
        payment.setCustomerId(customerId);
        payment.setAmount(amount);
        payment.setCurrency("USD");
        payment.setStatus("PENDING_FALLBACK");
        payment.setMessage("Processed via fallback");
        pendingPayments.put(transactionId, payment);
        return payment;
    }

    private PaymentResponseDto createPendingPayment(String transactionId) {
        PaymentResponseDto payment = new PaymentResponseDto();
        payment.setTransactionId(transactionId);
        payment.setCustomerId("unknown");
        payment.setAmount(BigDecimal.ZERO);
        payment.setCurrency("USD");
        payment.setStatus("UNKNOWN");
        return payment;
    }
}