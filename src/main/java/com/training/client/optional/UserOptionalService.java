package com.training.client.optional;

import com.training.client.dto.UserDto;
import java.util.Optional;

public class UserOptionalService {
    
    public Optional<String> getUsername(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return Optional.of("user" + id);
    }

    public String getUsernameOrDefault(Long id) {
        return getUsername(id).orElse("unknown");
    }

    public String getUsernameOrDefaultWithSupplier(Long id) {
        return getUsername(id).orElseGet(() -> "default_user");
    }

    public String getUsernameOrThrow(Long id) {
        return getUsername(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<UserDto> findUser(Long id) {
        if (id != null && id > 0) {
            UserDto user = new UserDto();
            user.setId(id);
            user.setUsername("user" + id);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public boolean isValidUser(UserDto user) {
        return Optional.ofNullable(user)
                .map(u -> u.getUsername() != null && u.getEmail() != null)
                .orElse(false);
    }
}