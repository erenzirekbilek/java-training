package com.training.client.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class UserTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionService.class);
    
    private final Map<Long, String> userStore = new ConcurrentHashMap<>();

    @Transactional
    public void createUser(Long id, String name) {
        logger.info("Creating user in transaction: {}", id);
        userStore.put(id, name);
    }

    @Transactional(readOnly = true)
    public String getUser(Long id) {
        return userStore.get(id);
    }

    @Transactional
    public void updateUser(Long id, String name) {
        logger.info("Updating user in transaction: {}", id);
        userStore.put(id, name);
    }

    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user in transaction: {}", id);
        userStore.remove(id);
    }
}