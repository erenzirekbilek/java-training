package com.training.client.client;

import com.training.client.adapter.UserResponseAdapter;
import com.training.client.dto.UserDto;
import com.training.client.exception.UserClientException;
import com.training.client.interfaces.UserClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Component
public class UserWebClient implements UserClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserWebClient.class);
    
    private final WebClient webClient;
    private final UserResponseAdapter responseAdapter;

    public UserWebClient(
            @Value("${external.api.user.base-url:https://jsonplaceholder.typicode.com}") String baseUrl,
            UserResponseAdapter responseAdapter) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.responseAdapter = responseAdapter;
    }

    @Override
    public UserDto getUserById(Long id) {
        try {
            logger.info("Fetching user with id: {}", id);
            UserDto user = webClient.get()
                    .uri("/users/{id}", id)
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .block(Duration.ofSeconds(10));
            return responseAdapter.adapt(user);
        } catch (Exception e) {
            throw new UserClientException("Error fetching user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            logger.info("Fetching all users");
            UserDto[] users = webClient.get()
                    .uri("/users")
                    .retrieve()
                    .bodyToMono(UserDto[].class)
                    .block(Duration.ofSeconds(10));
            return users != null ? responseAdapter.adaptList(Arrays.asList(users)) : Collections.emptyList();
        } catch (Exception e) {
            throw new UserClientException("Error fetching users: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto createUser(UserDto user) {
        try {
            logger.info("Creating user: {}", user.getUsername());
            UserDto created = webClient.post()
                    .uri("/users")
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .block(Duration.ofSeconds(10));
            return responseAdapter.adapt(created);
        } catch (Exception e) {
            throw new UserClientException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        try {
            logger.info("Updating user with id: {}", id);
            return webClient.put()
                    .uri("/users/{id}", id)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .block(Duration.ofSeconds(10));
        } catch (Exception e) {
            throw new UserClientException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            logger.info("Deleting user with id: {}", id);
            webClient.delete()
                    .uri("/users/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(Duration.ofSeconds(10));
        } catch (Exception e) {
            throw new UserClientException("Error deleting user: " + e.getMessage(), e);
        }
    }
}