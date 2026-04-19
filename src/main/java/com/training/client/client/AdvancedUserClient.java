package com.training.client.client;

import com.training.client.adapter.UserResponseAdapter;
import com.training.client.dto.UserDto;
import com.training.client.exception.UserClientException;
import com.training.client.interfaces.UserClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdvancedUserClient implements UserClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedUserClient.class);
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final UserResponseAdapter responseAdapter;

    @Value("${external.api.user.timeout.connect:5000}")
    private int connectTimeout;

    @Value("${external.api.user.timeout.read:10000}")
    private int readTimeout;

    public AdvancedUserClient(
            RestTemplate restTemplate,
            @Value("${external.api.user.base-url:https://jsonplaceholder.typicode.com}") String baseUrl,
            UserResponseAdapter responseAdapter) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.responseAdapter = responseAdapter;
    }

    @Override
    public UserDto getUserById(Long id) {
        try {
            String url = baseUrl + "/users/" + id;
            logger.info("Advanced client - Fetching user with id: {}", id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", generateRequestId());
            HttpEntity<?> request = new HttpEntity<>(headers);
            
            ResponseEntity<UserDto> response = restTemplate.exchange(
                url, HttpMethod.GET, request, UserDto.class);
            return responseAdapter.adapt(response.getBody());
        } catch (ResourceAccessException e) {
            throw new UserClientException("TIMEOUT_ERROR", "Connection timeout", e.getMessage(), e);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserClientException("NOT_FOUND", "User not found", e.getMessage(), e);
        } catch (Exception e) {
            throw new UserClientException("FETCH_ERROR", "Error fetching user", e.getMessage(), e);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            String url = baseUrl + "/users";
            logger.info("Advanced client - Fetching all users");
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", generateRequestId());
            HttpEntity<?> request = new HttpEntity<>(headers);
            
            ResponseEntity<UserDto[]> response = restTemplate.exchange(
                url, HttpMethod.GET, request, UserDto[].class);
            UserDto[] users = response.getBody();
            return users != null ? responseAdapter.adaptList(Arrays.asList(users)) : List.of();
        } catch (Exception e) {
            throw new UserClientException("FETCH_ALL_ERROR", "Error fetching users", e.getMessage(), e);
        }
    }

    @Override
    public UserDto createUser(UserDto user) {
        try {
            String url = baseUrl + "/users";
            logger.info("Advanced client - Creating user: {}", user.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Request-ID", generateRequestId());
            HttpEntity<UserDto> request = new HttpEntity<>(user, headers);
            
            ResponseEntity<UserDto> response = restTemplate.postForEntity(url, request, UserDto.class);
            return responseAdapter.adapt(response.getBody());
        } catch (Exception e) {
            throw new UserClientException("CREATE_ERROR", "Error creating user", e.getMessage(), e);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        try {
            String url = baseUrl + "/users/" + id;
            logger.info("Advanced client - Updating user with id: {}", id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Request-ID", generateRequestId());
            HttpEntity<UserDto> request = new HttpEntity<>(user, headers);
            
            ResponseEntity<UserDto> response = restTemplate.exchange(
                url, HttpMethod.PUT, request, UserDto.class);
            return responseAdapter.adapt(response.getBody());
        } catch (Exception e) {
            throw new UserClientException("UPDATE_ERROR", "Error updating user", e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            String url = baseUrl + "/users/" + id;
            logger.info("Advanced client - Deleting user with id: {}", id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Request-ID", generateRequestId());
            HttpEntity<?> request = new HttpEntity<>(headers);
            
            restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
        } catch (Exception e) {
            throw new UserClientException("DELETE_ERROR", "Error deleting user", e.getMessage(), e);
        }
    }

    private String generateRequestId() {
        return "req-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
}