package com.training.client.controller;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import com.training.client.dto.UserResponseDto;
import com.training.client.service.UserService;
import com.training.client.service.UserServiceWithFallback;
import com.training.client.adapter.UserResponseAdapter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retryable;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    private final UserServiceWithFallback fallbackService;
    private final UserResponseAdapter responseAdapter;

    public UserController(
            UserService userService,
            UserServiceWithFallback fallbackService,
            UserResponseAdapter responseAdapter) {
        this.userService = userService;
        this.fallbackService = fallbackService;
        this.responseAdapter = responseAdapter;
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        logger.info("Controller: GET /users/{}", id);
        UserDto user = userService.getUserById(id);
        UserResponseDto response = responseAdapter.adaptToResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.info("Controller: GET /users");
        List<UserDto> users = userService.getAllUsers();
        List<UserResponseDto> response = users.stream()
                .map(responseAdapter::adaptToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        logger.info("Controller: POST /users");
        UserDto user = userService.createUser(request);
        UserResponseDto response = responseAdapter.adaptToResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserRequestDto request) {
        logger.info("Controller: PUT /users/{}", id);
        UserDto user = userService.updateUser(id, request);
        UserResponseDto response = responseAdapter.adaptToResponse(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Controller: DELETE /users/{}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<UserResponseDto> getUserByIdFallback(Long id, Exception ex) {
        logger.warn("Fallback triggered for getUserById: {}", ex.getMessage());
        UserDto fallbackUser = fallbackService.getUserByIdFallback(id, ex);
        if (fallbackUser != null) {
            fallbackService.cacheUser(fallbackUser);
        }
        UserResponseDto response = responseAdapter.adaptToResponse(fallbackUser);
        return ResponseEntity.ok(response);
    }
}