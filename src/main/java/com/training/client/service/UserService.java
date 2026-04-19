package com.training.client.service;

import com.training.client.adapter.UserResponseAdapter;
import com.training.client.client.UserClient;
import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import com.training.client.dto.UserResponseDto;
import com.training.client.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserClient userClient;
    private final UserResponseAdapter responseAdapter;
    private final Map<Long, UserDto> userCache = new ConcurrentHashMap<>();

    public UserService(UserClient userClient, UserResponseAdapter responseAdapter) {
        this.userClient = userClient;
        this.responseAdapter = responseAdapter;
    }

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public UserDto getUserById(Long id) {
        logger.info("Service: Getting user by id: {}", id);
        if (userCache.containsKey(id)) {
            logger.info("Cache hit for user id: {}", id);
            return userCache.get(id);
        }
        UserDto user = userClient.getUserById(id);
        if (user != null) {
            userCache.put(id, user);
        }
        return user;
    }

    public List<UserDto> getAllUsers() {
        logger.info("Service: Getting all users");
        return userClient.getAllUsers();
    }

    public UserDto createUser(UserRequestDto request) {
        logger.info("Service: Creating user: {}", request.getUsername());
        UserDto user = responseAdapter.adaptFromRequest(
                new UserResponseDto(null, request.getUsername(), request.getEmail(), 
                        request.getFullName(), request.getAge())
        );
        return userClient.createUser(user);
    }

    public UserDto updateUser(Long id, UserRequestDto request) {
        logger.info("Service: Updating user id: {}", id);
        UserDto existing = getUserById(id);
        if (existing == null) {
            throw new UserNotFoundException(id);
        }
        existing.setUsername(request.getUsername());
        existing.setEmail(request.getEmail());
        existing.setFullName(request.getFullName());
        existing.setAge(request.getAge());
        UserDto updated = userClient.updateUser(id, existing);
        userCache.put(id, updated);
        return updated;
    }

    public void deleteUser(Long id) {
        logger.info("Service: Deleting user id: {}", id);
        userClient.deleteUser(id);
        userCache.remove(id);
    }

    public void clearCache() {
        logger.info("Service: Clearing user cache");
        userCache.clear();
    }
}