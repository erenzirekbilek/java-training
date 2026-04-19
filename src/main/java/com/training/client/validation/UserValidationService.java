package com.training.client.validation;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidationService implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "required", "Username is required");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            errors.rejectValue("email", "invalid", "Valid email is required");
        }
    }

    public static class UserDto {
        private String username;
        private String email;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}