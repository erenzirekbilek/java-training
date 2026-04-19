package com.training.client.service;

import com.training.client.dto.UserDto;
import com.training.client.exception.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserServiceWithFallback {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceWithFallback.class);
    
    private final Map<Long, UserDto> fallbackCache = new ConcurrentHashMap<>();

    public UserDto getUserByIdFallback(Long id, Exception ex) {
        logger.warn("Fallback triggered for getUserById: {} - Error: {}", id, ex.getMessage());
        UserDto cached = fallbackCache.get(id);
        if (cached != null) {
            logger.info("Returning cached user from fallback for id: {}", id);
            cached.setFullName(cached.getFullName() + " (from cache)");
            return cached;
        }
        return createDefaultUser(id);
    }

    public List<UserDto> getAllUsersFallback(Exception ex) {
        logger.warn("Fallback triggered for getAllUsers - Error: {}", ex.getMessage());
        if (!fallbackCache.isEmpty()) {
            logger.info("Returning cached users from fallback, count: {}", fallbackCache.size());
            return new ArrayList<>(fallbackCache.values());
        }
        return getDefaultUsers();
    }

    public UserDto createUserFallback(Exception ex) {
        logger.warn("Fallback triggered for createUser - Error: {}", ex.getMessage());
        throw new ServiceUnavailableException("UserService", "Cannot create user, service is unavailable");
    }

    public void cacheUser(UserDto user) {
        if (user != null && user.getId() != null) {
            fallbackCache.put(user.getId(), user);
            logger.info("User cached for fallback: {}", user.getId());
        }
    }

    private UserDto createDefaultUser(Long id) {
        UserDto defaultUser = new UserDto();
        defaultUser.setId(id);
        defaultUser.setUsername("user_" + id);
        defaultUser.setEmail("user" + id + "@example.com");
        defaultUser.setFullName("Default User " + id);
        defaultUser.setAge(25);
        defaultUser.setActive(true);
        return defaultUser;
    }

    private List<UserDto> getDefaultUsers() {
        List<UserDto> defaultUsers = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            defaultUsers.add(createDefaultUser(i));
        }
        return defaultUsers;
    }
}