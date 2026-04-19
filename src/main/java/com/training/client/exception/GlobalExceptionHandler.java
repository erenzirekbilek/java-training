package com.training.client.exception;

import com.training.client.response.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserClientException.class)
    public ResponseEntity<ApiErrorResponse> handleUserClientException(UserClientException ex) {
        logger.error("User client error: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getDetails()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                "USER_NOT_FOUND",
                ex.getMessage(),
                "User ID: " + ex.getUserId()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceUnavailableException(ServiceUnavailableException ex) {
        logger.error("Service unavailable: {} - {}", ex.getServiceName(), ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                "SERVICE_UNAVAILABLE",
                ex.getMessage(),
                "Service: " + ex.getServiceName()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.error("Validation error: {}", details);
        ApiErrorResponse response = new ApiErrorResponse(
                "VALIDATION_ERROR",
                "Invalid request data",
                details
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceAccessException(ResourceAccessException ex) {
        logger.error("Connection error: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                "CONNECTION_ERROR",
                "Failed to connect to external service",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpClientException(HttpClientErrorException ex) {
        logger.error("HTTP client error: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                "HTTP_ERROR",
                ex.getStatusText(),
                ex.getResponseBodyAsString()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        ApiErrorResponse response = new ApiErrorResponse(
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}