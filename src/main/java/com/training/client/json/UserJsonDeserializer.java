package com.training.client.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.training.client.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserJsonDeserializer extends JsonDeserializer<UserDto> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public UserDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var node = p.getCodec().treeAsTokens(p.currentToken());
        UserDto user = new UserDto();
        
        user.setId(node.has("id") ? node.get("id").asLong() : null);
        user.setUsername(node.has("username") ? node.get("username").asText() : null);
        user.setEmail(node.has("email") ? node.get("email").asText() : null);
        user.setFullName(node.has("fullName") ? node.get("fullName").asText() : null);
        user.setAge(node.has("age") ? node.get("age").asInt() : null);
        user.setActive(node.has("active") ? node.get("active").asBoolean() : true);
        
        if (node.has("createdAt")) {
            user.setCreatedAt(LocalDateTime.parse(node.get("createdAt").asText(), FORMATTER));
        }
        
        return user;
    }
}