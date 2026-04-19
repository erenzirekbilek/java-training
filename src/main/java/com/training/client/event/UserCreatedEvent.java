package com.training.client.event;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {
    private final Long userId;
    private final String username;

    public UserCreatedEvent(Object source, Long userId, String username) {
        super(source);
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
}