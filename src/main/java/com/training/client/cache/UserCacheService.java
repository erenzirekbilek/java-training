package com.training.client.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class UserCacheService {
    private static final Logger logger = LoggerFactory.getLogger(UserCacheService.class);
    
    private final Map<Long, String> cache = new ConcurrentHashMap<>();

    @Cacheable(value = "users", key = "#id")
    public String getUserById(Long id) {
        logger.info("Fetching user from source: {}", id);
        return "User-" + id;
    }

    @Cacheable(value = "usersAll")
    public Map<Long, String> getAllUsers() {
        logger.info("Fetching all users from source");
        return cache;
    }

    @CacheEvict(value = "users", key = "#id")
    public void evictUser(Long id) {
        logger.info("Evicting user from cache: {}", id);
    }

    @CacheEvict(value = "usersAll", allEntries = true)
    public void evictAllUsers() {
        logger.info("Evicting all users from cache");
    }

    public void put(Long id, String user) {
        cache.put(id, user);
    }
}