package com.training.client.idempotency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Component
public class IdempotencyKeyGenerator {
    private static final Logger logger = LoggerFactory.getLogger(IdempotencyKeyGenerator.class);

    public String generateKey(String... components) {
        String combined = String.join("|", components);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }

    public String generateKeyWithTimestamp(String operation, String userId, String payload) {
        String combined = operation + "|" + userId + "|" + payload + "|" + System.currentTimeMillis();
        return generateKey(combined);
    }

    public String generateKeyForUserAction(Long userId, String action) {
        return generateKey("user", userId.toString(), action);
    }
}