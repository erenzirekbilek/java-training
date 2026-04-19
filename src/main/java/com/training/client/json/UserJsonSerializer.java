package com.training.client.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.training.client.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserJsonSerializer extends JsonSerializer<UserDto> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void serialize(UserDto user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", user.getId());
        gen.writeStringField("username", user.getUsername());
        gen.writeStringField("email", user.getEmail());
        gen.writeStringField("fullName", user.getFullName());
        
        if (user.getAge() != null) {
            gen.writeNumberField("age", user.getAge());
        }
        
        gen.writeBooleanField("active", user.getActive() != null ? user.getActive() : true);
        
        if (user.getCreatedAt() != null) {
            gen.writeStringField("createdAt", user.getCreatedAt().format(FORMATTER));
        } else {
            gen.writeStringField("createdAt", LocalDateTime.now().format(FORMATTER));
        }
        
        gen.writeEndObject();
    }
}