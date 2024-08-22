package com.example.schoolmanage.repository;

import com.example.schoolmanage.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    boolean existsByEmail(String email);
    Customer findByCustomerId(long customerId);
    void deleteByCustomerId(long customerId);
}
