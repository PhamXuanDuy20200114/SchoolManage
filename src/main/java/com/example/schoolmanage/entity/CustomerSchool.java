package com.example.schoolmanage.entity;

import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name="schoolId", nullable = false)
    School school;
    String type;
    int status;
}
