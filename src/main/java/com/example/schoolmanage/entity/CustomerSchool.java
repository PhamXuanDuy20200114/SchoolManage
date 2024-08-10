package com.example.schoolmanage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CustomerSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long customerId;
    long schoolId;
    String type;
    int status;
}
