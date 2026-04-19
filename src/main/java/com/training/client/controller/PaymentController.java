package com.training.client.controller;

import com.training.client.dto.PaymentRequestDto;
import com.training.client.dto.PaymentResponseDto;
import com.training.client.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> processPayment(@Valid @RequestBody PaymentRequestDto request) {
        logger.info("Controller: POST /payments");
        PaymentResponseDto response = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/refund/{transactionId}")
    public ResponseEntity<PaymentResponseDto> refundPayment(@PathVariable String transactionId) {
        logger.info("Controller: POST /payments/refund/{}", transactionId);
        PaymentResponseDto response = paymentService.refundPayment(transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentResponseDto> getPaymentStatus(@PathVariable String transactionId) {
        logger.info("Controller: GET /payments/status/{}", transactionId);
        PaymentResponseDto response = paymentService.getPaymentStatus(transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentHistory(@PathVariable String customerId) {
        logger.info("Controller: GET /payments/history/{}", customerId);
        List<PaymentResponseDto> response = paymentService.getPaymentHistory(customerId);
        return ResponseEntity.ok(response);
    }
}