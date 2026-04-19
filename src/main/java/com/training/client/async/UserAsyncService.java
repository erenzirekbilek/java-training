package com.training.client.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(UserAsyncService.class);
    
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Async
    public CompletableFuture<String> getUserAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Async get user: {}", id);
            return "User-" + id;
        }, executor);
    }

    public CompletableFuture<String> processUserAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Processing user async: {}", id);
            return "Processed: " + id;
        }, executor);
    }
}