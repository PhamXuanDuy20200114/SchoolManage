package com.example.schoolmanage.repository;

import com.example.schoolmanage.entity.Customer;
import com.example.schoolmanage.entity.CustomerSchool;
import com.example.schoolmanage.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerSchoolRepository extends JpaRepository<CustomerSchool, String> {
    Page<CustomerSchool> findAllBySchool(School school, Pageable pageable);
    CustomerSchool findBySchoolAndCustomer(School school, Customer customer);
}
