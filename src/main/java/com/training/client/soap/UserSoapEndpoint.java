package com.training.client.soap;

import com.training.client.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
public class UserSoapEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(UserSoapEndpoint.class);
    private static final String NAMESPACE = "http://training.com/user";
    
    private final Map<Long, UserDto> userStore = new HashMap<>();

    public UserSoapEndpoint() {
        for (long i = 1; i <= 5; i++) {
            UserDto user = new UserDto(i, "user" + i, "user" + i + "@example.com", "User " + i, 25, true);
            userStore.put(i, user);
        }
    }

    @PayloadRoot(localPart = "GetUserRequest", namespace = NAMESPACE)
    @ResponsePayload
    public UserDto getUser(@RequestPayload Long id) {
        logger.info("SOAP endpoint - Get user: {}", id);
        return userStore.get(id);
    }

    @PayloadRoot(localPart = "CreateUserRequest", namespace = NAMESPACE)
    @ResponsePayload
    public UserDto createUser(@RequestPayload UserDto user) {
        logger.info("SOAP endpoint - Create user: {}", user.getUsername());
        long newId = userStore.size() + 1;
        user.setId(newId);
        userStore.put(newId, user);
        return user;
    }
}