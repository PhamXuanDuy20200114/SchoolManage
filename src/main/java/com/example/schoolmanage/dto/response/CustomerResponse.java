package com.example.schoolmanage.dto.response;

import com.example.schoolmanage.entity.CustomerSchool;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    long customerId;
    String email;
    String password;
    String name;
    String avt;
    String phone;
    Set<CustomerSchool> customerSchools;
}
