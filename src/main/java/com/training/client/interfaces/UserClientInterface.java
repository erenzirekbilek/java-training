package com.training.client.interfaces;

import com.training.client.dto.UserDto;
import java.util.List;

public interface UserClientInterface {
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto user);
    UserDto updateUser(Long id, UserDto user);
    void deleteUser(Long id);
}
