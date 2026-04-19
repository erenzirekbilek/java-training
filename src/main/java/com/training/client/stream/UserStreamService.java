package com.training.client.stream;

import com.training.client.dto.UserDto;
import java.util.*;
import java.util.stream.*;

public class UserStreamService {
    
    public List<UserDto> filterActiveUsers(List<UserDto> users) {
        return users.stream()
                .filter(UserDto::getActive)
                .collect(Collectors.toList());
    }

    public List<String> getUsernames(List<UserDto> users) {
        return users.stream()
                .map(UserDto::getUsername)
                .collect(Collectors.toList());
    }

    public List<UserDto> sortedByAge(List<UserDto> users) {
        return users.stream()
                .sorted(Comparator.comparingInt(UserDto::getAge))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findFirstAdult(List<UserDto> users) {
        return users.stream()
                .filter(u -> u.getAge() != null && u.getAge() >= 18)
                .findFirst();
    }

    public Map<String, List<UserDto>> groupByAgeRange(List<UserDto> users) {
        return users.stream()
                .collect(Collectors.groupingBy(u -> {
                    if (u.getAge() == null) return "unknown";
                    if (u.getAge() < 18) return "minor";
                    if (u.getAge() < 30) return "young";
                    return "adult";
                }));
    }

    public double getAverageAge(List<UserDto> users) {
        return users.stream()
                .filter(u -> u.getAge() != null)
                .mapToInt(UserDto::getAge)
                .average()
                .orElse(0.0);
    }
}