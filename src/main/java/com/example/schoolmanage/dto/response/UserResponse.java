package com.example.schoolmanage.dto.response;


import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class UserResponse {
    String email;
    String password;
    Set<String> roles;
}
