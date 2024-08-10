package com.example.schoolmanage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long schoolId;
    String address;
    String taxCode; // Mã số thuế
    String name;
    String avt;
    String payment_from;
    String payment_to;
    String schoolType; // 1-tiểu học, 2-cấp 2, 3-cấp 3
    String province; // Tỉnh thành
    String district; //Quận huyện
    String commune;  //Xã phường

    @OneToMany(mappedBy = "schoolId")
    Set<CustomerSchool> customerSchools;

}
