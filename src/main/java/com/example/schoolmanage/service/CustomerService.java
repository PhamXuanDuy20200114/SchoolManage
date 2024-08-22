package com.example.schoolmanage.service;

import com.example.schoolmanage.dto.request.CustomerRequest;
import com.example.schoolmanage.dto.response.CustomerResponse;
import com.example.schoolmanage.entity.Customer;
import com.example.schoolmanage.entity.CustomerSchool;
import com.example.schoolmanage.entity.School;
import com.example.schoolmanage.exception.AppException;
import com.example.schoolmanage.exception.ErrorCode;
import com.example.schoolmanage.repository.CustomerRepository;
import com.example.schoolmanage.repository.CustomerSchoolRepository;
import com.example.schoolmanage.repository.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    CustomerSchoolRepository customerSchoolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String create(CustomerRequest customerRequest) {
        if(!schoolRepository.existsBySchoolId(customerRequest.getSchoolId())){
            throw new AppException(ErrorCode.NOT_FOUND_OBJECT);
        }
        Customer customer = Customer.builder()
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .name(customerRequest.getName())
                .phone(customerRequest.getPhone())
                .build();

        CustomerSchool customerSchool = CustomerSchool.builder()
                .customer(customerRepository.save(customer))
                .school(schoolRepository.findBySchoolId(customerRequest.getSchoolId()))
                .type(customerRequest.getType())
                .status(customerRequest.getStatus())
                .build();
        customerSchoolRepository.save(customerSchool);
        return "Create customer successfully!";
    }

    public List<CustomerResponse> getListAdminSchoolBySchoolId(long schoolId, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        School school = schoolRepository.findBySchoolId(schoolId);
        Page<CustomerSchool> customerSchools = customerSchoolRepository.findAllBySchool(school, pageable);
        if(customerSchools.getTotalElements() == 0){
            throw new AppException(ErrorCode.NOT_FOUND_OBJECT);
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(CustomerSchool customerSchool : customerSchools.getContent()){
            Customer customer = customerSchool.getCustomer();
            customerResponses.add(CustomerResponse.builder()
                    .customerId(customer.getCustomerId())
                    .email(customer.getEmail())
                    .name(customer.getName())
                    .phone(customer.getPhone())
                    .avt(customer.getAvt())
                    .build());
        }
        return customerResponses;
    }

    //Xóa tài khoản trong trường
    public void deleteCustomerInSchool(long schoolId, long customerId){
        CustomerSchool customerSchool = customerSchoolRepository.findBySchoolAndCustomer(
                schoolRepository.findBySchoolId(schoolId),
                customerRepository.findByCustomerId(customerId)
        );
        customerSchoolRepository.delete(customerSchool);
    }

    public List<CustomerResponse> getListCustomer(int page, int perPage){
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers){
            customerResponses.add(
                    CustomerResponse.builder()
                            .customerId(customer.getCustomerId())
                            .email(customer.getEmail())
                            .name(customer.getName())
                            .phone(customer.getPhone())
                            .avt(customer.getAvt())
                            .build()
            );
        }
        return customerResponses;
    }

    public CustomerResponse getCustomer(long customerId){
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer == null){
            throw new AppException(ErrorCode.NOT_FOUND_OBJECT);
        }
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .avt(customer.getAvt())
                .build();
    }

    //Cập nhật tài khoản trong trường
    public void update(long customerId, CustomerRequest customerRequest){
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer == null){
            throw new AppException(ErrorCode.NOT_FOUND_OBJECT);
        }
        customer.setName(customerRequest.getName());
        customer.setPhone(customerRequest.getPhone());
        customer.setAvt(customerRequest.getAvt());
        customer.setEmail(customerRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customerRepository.save(customer);
        CustomerSchool customerSchool = customerSchoolRepository.findBySchoolAndCustomer(
                schoolRepository.findBySchoolId(customerRequest.getSchoolId()),
                customerRepository.findByCustomerId(customerId)
        );
        customerSchool.setType(customerRequest.getType());
        customerSchool.setStatus(customerRequest.getStatus());
        customerSchoolRepository.save(customerSchool);
    }
}
