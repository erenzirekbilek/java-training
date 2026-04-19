package com.training.client.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserMetricsService {
    private final Counter userCreatedCounter;
    private final Counter userFetchedCounter;
    private final Timer fetchTimer;

    public UserMetricsService(MeterRegistry registry) {
        this.userCreatedCounter = Counter.builder("user.created")
                .description("Number of users created")
                .register(registry);
        this.userFetchedCounter = Counter.builder("user.fetched")
                .description("Number of users fetched")
                .register(registry);
        this.fetchTimer = Timer.builder("user.fetch.time")
                .description("Time to fetch users")
                .register(registry);
    }

    public void recordUserCreated() {
        userCreatedCounter.increment();
    }

    public void recordUserFetched() {
        userFetchedCounter.increment();
    }

    public void recordFetchTime(long millis) {
        fetchTimer.record(millis, TimeUnit.MILLISECONDS);
    }
}