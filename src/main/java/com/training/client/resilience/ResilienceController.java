package com.training.client.resilience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/resilience")
public class ResilienceController {
    private static final Logger logger = LoggerFactory.getLogger(ResilienceController.class);
    
    private final CombinedResilienceService service;

    public ResilienceController(CombinedResilienceService service) {
        this.service = service;
    }

    @GetMapping("/execute/{operation}")
    public ResponseEntity<String> execute(@PathVariable String operation) {
        logger.info("Resilience controller - execute: {}", operation);
        String result = service.executeWithAllPatterns(operation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}