package com.training.client.client;

import com.training.client.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AsyncUserClient {
    private static final Logger logger = LoggerFactory.getLogger(AsyncUserClient.class);
    
    private final RestTemplate restTemplate;
    private final ExecutorService executor;

    public AsyncUserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executor = Executors.newFixedThreadPool(5);
    }

    public CompletableFuture<UserDto> getUserAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Async fetch user: {}", id);
            return restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/" + id, UserDto.class);
        }, executor);
    }

    public CompletableFuture<List<UserDto>> getAllUsersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Async fetch all users");
            UserDto[] users = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", UserDto[].class);
            return users != null ? List.of(users) : List.of();
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
}