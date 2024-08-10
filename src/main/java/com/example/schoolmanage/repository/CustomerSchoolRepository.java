package com.example.schoolmanage.repository;

import com.example.schoolmanage.entity.CustomerSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSchoolRepository extends JpaRepository<CustomerSchool, String> {
}
