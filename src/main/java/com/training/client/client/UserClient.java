package com.training.client.client;

import com.training.client.adapter.UserResponseAdapter;
import com.training.client.dto.UserDto;
import com.training.client.exception.UserClientException;
import com.training.client.interfaces.UserClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Component
public class UserClient implements UserClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final UserResponseAdapter responseAdapter;

    public UserClient(
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
            logger.info("Fetching user with id: {}", id);
            UserDto user = restTemplate.getForObject(url, UserDto.class);
            return responseAdapter.adapt(user);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserClientException("User not found with id: " + id, e);
        } catch (ResourceAccessException e) {
            throw new UserClientException("Failed to connect to user service", e);
        } catch (Exception e) {
            throw new UserClientException("Error fetching user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            String url = baseUrl + "/users";
            logger.info("Fetching all users");
            UserDto[] users = restTemplate.getForObject(url, UserDto[].class);
            return users != null ? responseAdapter.adaptList(Arrays.asList(users)) : Collections.emptyList();
        } catch (ResourceAccessException e) {
            throw new UserClientException("Failed to connect to user service", e);
        } catch (Exception e) {
            throw new UserClientException("Error fetching users: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto createUser(UserDto user) {
        try {
            String url = baseUrl + "/users";
            logger.info("Creating user: {}", user.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDto> request = new HttpEntity<>(user, headers);
            UserDto created = restTemplate.postForObject(url, request, UserDto.class);
            return responseAdapter.adapt(created);
        } catch (Exception e) {
            throw new UserClientException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        try {
            String url = baseUrl + "/users/" + id;
            logger.info("Updating user with id: {}", id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDto> request = new HttpEntity<>(user, headers);
            return restTemplate.exchange(url, HttpMethod.PUT, request, UserDto.class).getBody();
        } catch (Exception e) {
            throw new UserClientException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            String url = baseUrl + "/users/" + id;
            logger.info("Deleting user with id: {}", id);
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new UserClientException("Error deleting user: " + e.getMessage(), e);
        }
    }
}