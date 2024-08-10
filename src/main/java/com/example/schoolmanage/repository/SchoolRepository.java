package com.example.schoolmanage.repository;

import com.example.schoolmanage.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
}
