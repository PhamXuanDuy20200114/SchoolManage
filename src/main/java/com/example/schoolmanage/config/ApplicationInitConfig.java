package com.example.schoolmanage.config;


import com.example.schoolmanage.entity.UserEntity;
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

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args->{
            if(!userRepository.existsByEmail("admin@admin.com")){
                UserEntity user = UserEntity.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .build();
                log.warn("User admin has been created with default password: admin");
                userRepository.save(user);
            }
        };
    };

}
