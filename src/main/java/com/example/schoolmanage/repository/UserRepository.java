package com.example.schoolmanage.repository;

import com.example.schoolmanage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
