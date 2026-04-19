package com.training.client.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class BulkheadService {
    private static final Logger logger = LoggerFactory.getLogger(BulkheadService.class);
    
    private final BulkheadRegistry bulkheadRegistry;

    public BulkheadService() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(10)
                .maxWaitDuration(Duration.ofSeconds(5))
                .build();
        this.bulkheadRegistry = BulkheadRegistry.of(config);
    }

    public <T> T executeWithBulkhead(Supplier<T> supplier, String name) {
        logger.info("Executing with bulkhead: {}", name);
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(name);
        return Bulkhead.decorateSupplier(bulkhead, supplier).get();
    }

    public void executeWithBulkhead(Runnable operation, String name) {
        logger.info("Executing with bulkhead (runnable): {}", name);
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(name);
        Bulkhead.decorateRunnable(bulkhead, operation).run();
    }

    public int getAvailableCalls(String name) {
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(name);
        return bulkhead.getMetrics().getAvailableConcurrentCalls();
    }
}