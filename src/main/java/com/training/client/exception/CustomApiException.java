package com.training.client.exception;

public class CustomApiException extends RuntimeException {
    private final int statusCode;

    public CustomApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public CustomApiException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}