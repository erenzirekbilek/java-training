package com.training.client.ratelimit;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RateLimitInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    
    private final RateLimiterService rateLimiterService;

    public RateLimitInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @RateLimiter(name = "default", fallbackMethod = "rateLimitFallback")
    public String executeRequest(String endpoint) {
        logger.info("Executing request: {}", endpoint);
        return "Request processed: " + endpoint;
    }

    public String rateLimitFallback(String endpoint, Throwable t) {
        logger.warn("Rate limit exceeded for: {} - {}", endpoint, t.getMessage());
        return "Rate limit exceeded. Please try again later.";
    }

    public boolean checkRateLimit(String clientId) {
        boolean allowed = rateLimiterService.tryAcquire(clientId);
        if (!allowed) {
            logger.warn("Rate limit denied for client: {}", clientId);
        }
        return allowed;
    }
}