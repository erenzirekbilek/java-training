package com.training.client.client;

import com.training.client.dto.UserDto;
import com.training.client.exception.UserClientException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Component
public class ResilienceUserClientWrapper {
    private static final Logger logger = LoggerFactory.getLogger(ResilienceUserClientWrapper.class);
    
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public ResilienceUserClientWrapper(CircuitBreaker circuitBreaker, Retry retry) {
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
    }

    public <T> T executeWithResilience(Supplier<T> supplier, String operation) {
        logger.info("Executing with resilience: {}", operation);
        try {
            Supplier<T> decorated = CircuitBreaker.decorateSupplier(circuitBreaker, supplier);
            decorated = Retry.decorateSupplier(retry, decorated);
            return decorated.get();
        } catch (Exception e) {
            throw new UserClientException("RESILIENCE_ERROR", "Operation failed: " + operation, e.getMessage(), e);
        }
    }

    public CircuitBreaker.State getCircuitBreakerState() {
        return circuitBreaker.getState();
    }
}