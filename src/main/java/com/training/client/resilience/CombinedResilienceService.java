package com.training.client.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CombinedResilienceService {
    private static final Logger logger = LoggerFactory.getLogger(CombinedResilienceService.class);
    
    private final BulkheadService bulkheadService;
    private final RateLimiterService rateLimiterService;

    public CombinedResilienceService(BulkheadService bulkheadService, RateLimiterService rateLimiterService) {
        this.bulkheadService = bulkheadService;
        this.rateLimiterService = rateLimiterService;
    }

    @Retry(name = "combined", maxAttempts = 3)
    public String executeWithAllPatterns(String operation) {
        logger.info("Executing with combined resilience: {}", operation);
        String result = bulkheadService.executeWithBulkhead(() -> {
            return rateLimiterService.executeWithRateLimit(() -> {
                return "Result: " + operation;
            }, "operation");
        }, "operation");
        return result;
    }

    public String executeWithCircuitBreaker(Supplier<String> supplier, String name) {
        return supplier.get();
    }
}