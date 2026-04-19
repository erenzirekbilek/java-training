package com.training.client.exception;

public class UserNotFoundException extends RuntimeException {
    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
        this.userId = userId;
    }

    public UserNotFoundException(Long userId, Throwable cause) {
        super("User not found with id: " + userId, cause);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}