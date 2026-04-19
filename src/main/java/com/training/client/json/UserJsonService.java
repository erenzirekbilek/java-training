package com.training.client.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Map;

public class UserJsonService {
    private final ObjectMapper mapper;
    
    public UserJsonService() {
        this.mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String toJson(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    public Map<String, Object> toMap(Object obj) {
        return mapper.convertValue(obj, Map.class);
    }
}