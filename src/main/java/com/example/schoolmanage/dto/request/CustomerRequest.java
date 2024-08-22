package com.example.schoolmanage.dto.request;

import com.example.schoolmanage.entity.CustomerSchool;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequest {
    String email;
    String password;
    String name;
    String avt;
    String phone;
    long schoolId;
    String type;
    int status;
}
