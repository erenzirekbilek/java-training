package com.training.client.client;

import com.training.client.dto.UserDto;
import com.training.client.interfaces.UserClientInterface;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MockUserClient implements UserClientInterface {
    private final Map<Long, UserDto> userStore = new ConcurrentHashMap<>();

    public MockUserClient() {
        for (long i = 1; i <= 10; i++) {
            UserDto user = new UserDto();
            user.setId(i);
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@example.com");
            user.setFullName("User " + i);
            user.setAge(25);
            user.setActive(true);
            userStore.put(i, user);
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        return userStore.get(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userStore.values().stream().collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto user) {
        Long newId = (long) (userStore.size() + 1);
        user.setId(newId);
        userStore.put(newId, user);
        return user;
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        user.setId(id);
        userStore.put(id, user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userStore.remove(id);
    }
}