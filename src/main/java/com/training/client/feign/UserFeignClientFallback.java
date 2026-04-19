package com.training.client.feign;

import com.training.client.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public UserDto getUserById(Long id) {
        UserDto user = new UserDto();
        user.setId(id);
        user.setUsername("fallback_user_" + id);
        user.setEmail("fallback" + id + "@example.com");
        user.setFullName("Fallback User " + id);
        user.setActive(true);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return IntStream.rangeClosed(1, 10)
                .mapToObj(i -> {
                    UserDto user = new UserDto();
                    user.setId((long) i);
                    user.setUsername("fallback_user_" + i);
                    user.setEmail("fallback" + i + "@example.com");
                    user.setFullName("Fallback User " + i);
                    user.setActive(true);
                    return user;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto user) {
        user.setId(999L);
        return user;
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        user.setId(id);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
    }
}