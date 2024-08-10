package com.example.schoolmanage.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    int code = 200;
    String message = "Successful!";
    T data;
}
