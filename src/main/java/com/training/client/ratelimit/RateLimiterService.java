package com.training.client.ratelimit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class RateLimiterService {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);
    
    private final RateLimiterRegistry registry;

    public RateLimiterService() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .build();
        this.registry = RateLimiterRegistry.of(config);
    }

    public <T> T executeWithRateLimit(Supplier<T> supplier, String name) {
        logger.info("Executing with rate limit: {}", name);
        RateLimiter rateLimiter = registry.rateLimiter(name);
        return RateLimiter.decorateSupplier(rateLimiter, supplier).get();
    }

    public boolean tryAcquire(String name) {
        RateLimiter rateLimiter = registry.rateLimiter(name);
        return rateLimiter.acquirePermission();
    }

    public int getAvailablePermissions(String name) {
        RateLimiter rateLimiter = registry.rateLimiter(name);
        return rateLimiter.getMetrics().getAvailablePermissions();
    }
}