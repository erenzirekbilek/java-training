package com.training.client.exception;

public class UserClientException extends RuntimeException {
    private final String errorCode;
    private final String details;

    public UserClientException(String message) {
        super(message);
        this.errorCode = "USER_CLIENT_ERROR";
        this.details = null;
    }

    public UserClientException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "USER_CLIENT_ERROR";
        this.details = null;
    }

    public UserClientException(String errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public UserClientException(String errorCode, String message, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}