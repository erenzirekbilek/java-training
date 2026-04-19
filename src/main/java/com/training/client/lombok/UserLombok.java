package com.training.client.lombok;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLombok {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Integer age;
    private Boolean active;
}