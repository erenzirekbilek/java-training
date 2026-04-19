package com.training.client.interfaces;

import com.training.client.dto.PaymentRequestDto;
import com.training.client.dto.PaymentResponseDto;
import java.util.List;

public interface PaymentGatewayInterface {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    PaymentResponseDto refundPayment(String transactionId);
    PaymentResponseDto getPaymentStatus(String transactionId);
    List<PaymentResponseDto> getPaymentHistory(String customerId);
}