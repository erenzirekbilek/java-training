package com.training.client.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ExternalApiConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiConsumer.class);
    
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ExternalApiConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = "https://jsonplaceholder.typicode.com";
    }

    public Map<String, Object> fetchFromApi(String endpoint) {
        logger.info("Fetching from external API: {}", endpoint);
        try {
            return restTemplate.getForObject(baseUrl + endpoint, Map.class);
        } catch (Exception e) {
            logger.error("External API error", e);
            return Map.of();
        }
    }

    public Map<String, Object> postToApi(String endpoint, Object payload) {
        logger.info("Posting to external API: {}", endpoint);
        try {
            return restTemplate.postForObject(baseUrl + endpoint, payload, Map.class);
        } catch (Exception e) {
            logger.error("External API post error", e);
            return Map.of();
        }
    }
}