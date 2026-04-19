package com.training.client.feign;

import com.training.client.dto.UserDto;
import com.training.client.dto.UserRequestDto;
import com.training.client.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "userFeignClient", url = "${external.api.user.base-url:https://jsonplaceholder.typicode.com}")
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/users")
    List<UserDto> getAllUsers();

    @PostMapping("/users")
    UserDto createUser(@RequestBody UserDto user);

    @PutMapping("/users/{id}")
    UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserDto user);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id);
}