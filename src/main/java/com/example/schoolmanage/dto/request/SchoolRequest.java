package com.example.schoolmanage.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchoolRequest {
    String address;
    String taxCode; // Mã số thuế
    String name;
    String avt;
    String payment_from;
    String payment_to;
    String schoolType; // 1-tiểu học, 2-cấp 2, 3-cấp 3
    String province; // Tỉnh thành
    String district; //Quận huyện
    String commune;
}
