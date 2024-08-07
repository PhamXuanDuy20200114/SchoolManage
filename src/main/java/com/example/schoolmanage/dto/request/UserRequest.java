package com.example.schoolmanage.dto.request;


import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class UserRequest {
    String email;
    String password;
    Set<String> roles;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldNameConstants(level = AccessLevel.PRIVATE)
    public static class UserResponse {
        String email;
        String password;
        Set<String> roles;
    }
}
