package com.training.client.integration;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import com.training.client.dto.UserResponseDto;
import com.training.client.service.UserService;
import com.training.client.adapter.UserResponseAdapter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retryable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserIntegrationService {
    private static final Logger logger = LoggerFactory.getLogger(UserIntegrationService.class);
    
    private final UserService userService;
    private final UserResponseAdapter adapter;

    public UserIntegrationService(UserService userService, UserResponseAdapter adapter) {
        this.userService = userService;
        this.adapter = adapter;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retryable(maxAttempts = 3, backoff = @io.github.resilience4j.retry.annotation.Backoff(delay = 1000))
    public UserResponseDto getUserById(Long id) {
        logger.info("Integration service - Getting user by id: {}", id);
        UserDto user = userService.getUserById(id);
        return adapter.adaptToResponse(user);
    }

    public List<UserResponseDto> getAllUsers() {
        logger.info("Integration service - Getting all users");
        return userService.getAllUsers().stream()
                .map(adapter::adaptToResponse)
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "createUserFallback")
    public UserResponseDto createUser(UserRequestDto request) {
        logger.info("Integration service - Creating user: {}", request.getUsername());
        UserDto user = userService.createUser(request);
        return adapter.adaptToResponse(user);
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "updateUserFallback")
    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        logger.info("Integration service - Updating user: {}", id);
        UserDto user = userService.updateUser(id, request);
        return adapter.adaptToResponse(user);
    }

    public void deleteUser(Long id) {
        logger.info("Integration service - Deleting user: {}", id);
        userService.deleteUser(id);
    }

    public UserResponseDto getUserFallback(Long id, Exception ex) {
        logger.warn("Fallback for getUserById: {}", ex.getMessage());
        return new UserResponseDto(id, "fallback_user", "fallback@example.com", "Fallback User", 0);
    }

    public UserResponseDto createUserFallback(UserRequestDto request, Exception ex) {
        logger.warn("Fallback for createUser: {}", ex.getMessage());
        return new UserResponseDto(0L, request.getUsername(), request.getEmail(), request.getFullName(), request.getAge());
    }

    public UserResponseDto updateUserFallback(Long id, UserRequestDto request, Exception ex) {
        logger.warn("Fallback for updateUser: {}", ex.getMessage());
        return new UserResponseDto(id, request.getUsername(), request.getEmail(), request.getFullName(), request.getAge());
    }
}