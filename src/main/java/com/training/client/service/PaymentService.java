package com.training.client.service;

import com.training.client.dto.PaymentRequestDto;
import com.training.client.dto.PaymentResponseDto;
import com.training.client.interfaces.PaymentGatewayInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    
    private final PaymentGatewayInterface paymentGateway;

    public PaymentService(PaymentGatewayInterface paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        logger.info("Processing payment for customer: {}", request.getCustomerId());
        return paymentGateway.processPayment(request);
    }

    public PaymentResponseDto refundPayment(String transactionId) {
        logger.info("Processing refund for transaction: {}", transactionId);
        return paymentGateway.refundPayment(transactionId);
    }

    public PaymentResponseDto getPaymentStatus(String transactionId) {
        logger.info("Getting payment status for: {}", transactionId);
        return paymentGateway.getPaymentStatus(transactionId);
    }

    public List<PaymentResponseDto> getPaymentHistory(String customerId) {
        logger.info("Getting payment history for customer: {}", customerId);
        return paymentGateway.getPaymentHistory(customerId);
    }
}