package com.example.schoolmanage.config;


import com.example.schoolmanage.entity.Role;
import com.example.schoolmanage.entity.User;
import com.example.schoolmanage.exception.AppException;
import com.example.schoolmanage.exception.ErrorCode;
import com.example.schoolmanage.repository.RoleRepository;
import com.example.schoolmanage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args->{
            if(!userRepository.existsByEmail("admin@gmail.com")){
                Role adminRole = new Role().builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .build();
                roleRepository.save(adminRole);
                HashSet<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                log.warn("User admin has been created with default password: 12345678");
                userRepository.save(user);
            }
        };
    };

}
