package com.training.client.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenService {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);
    
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public String generateToken(String username) {
        logger.info("Generating token for: {}", username);
        String payload = username + ":" + System.currentTimeMillis();
        String token = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        tokenStore.put(token, username);
        return token;
    }

    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    public String getUsernameFromToken(String token) {
        return tokenStore.get(token);
    }

    public void revokeToken(String token) {
        logger.info("Revoking token");
        tokenStore.remove(token);
    }
}