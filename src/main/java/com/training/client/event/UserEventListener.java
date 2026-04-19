package com.training.client.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        logger.info("User created event received: {} - {}", event.getUserId(), event.getUsername());
    }

    @Async
    @EventListener
    public void handleUserCreatedAsync(UserCreatedEvent event) {
        logger.info("Async - User created: {}", event.getUsername());
    }
}