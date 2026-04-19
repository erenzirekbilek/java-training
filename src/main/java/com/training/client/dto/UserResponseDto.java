package com.training.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponseDto {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("fullName")
    private String fullName;
    
    @JsonProperty("age")
    private Integer age;
    
    @JsonProperty("status")
    private String status;

    public UserResponseDto() {}

    public UserResponseDto(Long id, String username, String email, String fullName, Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.status = "success";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}