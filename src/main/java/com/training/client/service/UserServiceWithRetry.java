package com.training.client.service;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceWithRetry {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceWithRetry.class);
    
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public UserDto getUserByIdWithRetry(Long id) {
        logger.info("Retry service - Getting user by id: {}", id);
        throw new RuntimeException("Simulated failure for retry");
    }

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public List<UserDto> getAllUsersWithRetry() {
        logger.info("Retry service - Getting all users with retry");
        throw new RuntimeException("Simulated failure for retry");
    }

    @Recover
    public UserDto recoverGetUserById(Exception e, Long id) {
        logger.warn("Recovery for getUserById - Error: {}", e.getMessage());
        UserDto defaultUser = new UserDto();
        defaultUser.setId(id);
        defaultUser.setUsername("default_user");
        defaultUser.setEmail("default@example.com");
        defaultUser.setFullName("Default User");
        return defaultUser;
    }

    @Recover
    public List<UserDto> recoverGetAllUsers(Exception e) {
        logger.warn("Recovery for getAllUsers - Error: {}", e.getMessage());
        return List.of();
    }
}