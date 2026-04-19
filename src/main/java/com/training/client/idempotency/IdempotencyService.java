package com.training.client.idempotency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class IdempotencyService {
    private static final Logger logger = LoggerFactory.getLogger(IdempotencyService.class);
    
    private final Map<String, IdempotencyRecord> store = new ConcurrentHashMap<>();
    private final IdempotencyKeyGenerator keyGenerator;

    public IdempotencyService(IdempotencyKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public boolean isIdempotent(String key) {
        if (store.containsKey(key)) {
            IdempotencyRecord record = store.get(key);
            if (record.isExpired()) {
                store.remove(key);
                return false;
            }
            logger.info("Idempotent request found: {}", key);
            return true;
        }
        return false;
    }

    public void recordRequest(String key, Object result) {
        logger.info("Recording idempotent request: {}", key);
        store.put(key, new IdempotencyRecord(result, LocalDateTime.now().plusHours(24)));
    }

    public Object getResult(String key) {
        IdempotencyRecord record = store.get(key);
        return record != null ? record.getResult() : null;
    }

    public static class IdempotencyRecord {
        private final Object result;
        private final LocalDateTime expiresAt;

        public IdempotencyRecord(Object result, LocalDateTime expiresAt) {
            this.result = result;
            this.expiresAt = expiresAt;
        }

        public Object getResult() { return result; }
        public boolean isExpired() { return LocalDateTime.now().isAfter(expiresAt); }
    }
}