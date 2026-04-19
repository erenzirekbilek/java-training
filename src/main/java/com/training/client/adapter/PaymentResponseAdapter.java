package com.training.client.adapter;

import com.training.client.dto.PaymentRequestDto;
import com.training.client.dto.PaymentResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentResponseAdapter {

    public PaymentResponseDto adapt(PaymentRequestDto source) {
        if (source == null) {
            return null;
        }
        
        PaymentResponseDto target = new PaymentResponseDto();
        target.setCustomerId(source.getCustomerId());
        target.setAmount(source.getAmount());
        target.setCurrency(source.getCurrency());
        target.setStatus("PENDING");
        
        return target;
    }

    public List<PaymentResponseDto> adaptList(List<PaymentRequestDto> sources) {
        if (sources == null) {
            return List.of();
        }
        return sources.stream()
                .map(this::adapt)
                .collect(Collectors.toList());
    }
}