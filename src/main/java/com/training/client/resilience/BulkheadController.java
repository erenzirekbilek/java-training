package com.training.client.resilience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bulkhead")
public class BulkheadController {
    private static final Logger logger = LoggerFactory.getLogger(BulkheadController.class);
    
    private final BulkheadService bulkheadService;

    public BulkheadController(BulkheadService bulkheadService) {
        this.bulkheadService = bulkheadService;
    }

    @GetMapping("/execute/{name}")
    public ResponseEntity<String> execute(@PathVariable String name) {
        logger.info("Bulkhead execute: {}", name);
        String result = bulkheadService.executeWithBulkhead(() -> {
            try {
                Thread.sleep(100);
                return "Executed: " + name;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Interrupted";
            }
        }, name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/available/{name}")
    public ResponseEntity<Integer> getAvailable(@PathVariable String name) {
        int available = bulkheadService.getAvailableCalls(name);
        return ResponseEntity.ok(available);
    }
}