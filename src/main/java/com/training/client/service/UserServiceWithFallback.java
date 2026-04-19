package com.training.client.service;

import com.training.client.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceWithFallback {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceWithFallback.class);
    
    private final Map<Long, UserDto> cache = new ConcurrentHashMap<>();

    public UserDto getFallbackUser(Long id) {
        logger.warn("Returning fallback user for id: {}", id);
        if (cache.containsKey(id)) {
            UserDto cached = cache.get(id);
            cached.setUsername(cached.getUsername() + "_cached");
            return cached;
        }
        return createDefaultUser(id);
    }

    public List<UserDto> getAllFallbackUsers() {
        logger.warn("Returning all fallback users from cache");
        return cache.values().stream().toList();
    }

    public void cacheUser(UserDto user) {
        if (user != null && user.getId() != null) {
            cache.put(user.getId(), user);
        }
    }

    private UserDto createDefaultUser(Long id) {
        UserDto user = new UserDto();
        user.setId(id);
        user.setUsername("fallback_user_" + id);
        user.setEmail("fallback" + id + "@example.com");
        user.setFullName("Fallback User " + id);
        user.setAge(0);
        user.setActive(true);
        return user;
    }
}