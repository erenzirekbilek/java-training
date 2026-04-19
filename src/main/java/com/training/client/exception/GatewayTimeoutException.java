package com.training.client.exception;

public class GatewayTimeoutException extends RuntimeException {
    private final String operation;

    public GatewayTimeoutException(String operation, String message) {
        super(message);
        this.operation = operation;
    }

    public GatewayTimeoutException(String operation, String message, Throwable cause) {
        super(message, cause);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}