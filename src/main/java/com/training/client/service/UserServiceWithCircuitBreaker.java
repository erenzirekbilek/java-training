package com.training.client.service;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserServiceWithCircuitBreaker {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceWithCircuitBreaker.class);
    
    private final CircuitBreaker circuitBreaker;

    public UserServiceWithCircuitBreaker(CircuitBreakerRegistry registry) {
        this.circuitBreaker = registry.circuitBreaker("userService");
    }

    public UserDto getUserByIdWithCircuitBreaker(Long id, Supplier<UserDto> supplier) {
        logger.info("Circuit breaker - Getting user by id: {}", id);
        return CircuitBreaker.decorateSupplier(circuitBreaker, supplier).get();
    }

    public List<UserDto> getAllUsersWithCircuitBreaker(Supplier<List<UserDto>> supplier) {
        logger.info("Circuit breaker - Getting all users");
        return CircuitBreaker.decorateSupplier(circuitBreaker, supplier).get();
    }

    public String getCircuitBreakerState() {
        return circuitBreaker.getState().toString();
    }

    public float getFailureRate() {
        return circuitBreaker.getMetrics().getFailureRate();
    }
}