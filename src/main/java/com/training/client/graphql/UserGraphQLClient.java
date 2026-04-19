package com.training.client.graphql;

import com.training.client.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserGraphQLClient {
    private static final Logger logger = LoggerFactory.getLogger(UserGraphQLClient.class);
    
    private final RestTemplate restTemplate;
    private final String graphqlUrl;

    public UserGraphQLClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.graphqlUrl = "https://countries.trevorblades.com/graphql";
    }

    public List<UserDto> getUsersByQuery(String query) {
        logger.info("Executing GraphQL query: {}", query);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        
        try {
            Map response = restTemplate.postForObject(graphqlUrl, requestBody, Map.class);
            logger.info("GraphQL response received");
            return List.of();
        } catch (Exception e) {
            logger.error("GraphQL error", e);
            return List.of();
        }
    }

    public Map<String, Object> executeMutation(String mutation, Map<String, Object> variables) {
        logger.info("Executing GraphQL mutation");
        Map<String, Object> request = new HashMap<>();
        request.put("query", mutation);
        request.put("variables", variables);
        
        try {
            return restTemplate.postForObject(graphqlUrl, request, Map.class);
        } catch (Exception e) {
            logger.error("GraphQL mutation error", e);
            return Map.of();
        }
    }
}