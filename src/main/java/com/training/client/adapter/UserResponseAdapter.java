package com.training.client.adapter;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserResponseAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserResponseAdapter.class);

    public UserDto adapt(UserDto source) {
        if (source == null) {
            return null;
        }
        
        UserDto target = new UserDto();
        target.setId(source.getId());
        target.setUsername(source.getUsername());
        target.setEmail(source.getEmail());
        target.setFullName(source.getFullName());
        
        if (source.getAge() != null) {
            target.setAge(source.getAge());
        }
        
        target.setActive(source.getActive() != null ? source.getActive() : true);
        
        return target;
    }

    public List<UserDto> adaptList(List<UserDto> sources) {
        if (sources == null) {
            return List.of();
        }
        return sources.stream()
                .map(this::adapt)
                .collect(Collectors.toList());
    }

    public UserResponseDto adaptToResponse(UserDto source) {
        if (source == null) {
            return null;
        }
        return new UserResponseDto(
                source.getId(),
                source.getUsername(),
                source.getEmail(),
                source.getFullName(),
                source.getAge()
        );
    }

    public UserDto adaptFromRequest(UserResponseDto source) {
        if (source == null) {
            return null;
        }
        UserDto target = new UserDto();
        target.setUsername(source.getUsername());
        target.setEmail(source.getEmail());
        target.setFullName(source.getFullName());
        target.setAge(source.getAge());
        target.setActive(true);
        return target;
    }
}