package com.example.schoolmanage.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticateRequest {
    String email;
    String password;
    Set<String> roles;
}
