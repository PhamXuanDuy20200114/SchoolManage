package com.example.schoolmanage.controller;

import com.example.schoolmanage.dto.ApiResponse;
import com.example.schoolmanage.dto.request.GetAllRequest;
import com.example.schoolmanage.dto.request.SchoolRequest;
import com.example.schoolmanage.dto.response.SchoolResponse;
import com.example.schoolmanage.service.SchoolService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping
    public ApiResponse<List<SchoolResponse>> getAll(@RequestParam int page, @RequestParam int perPage) {
        return ApiResponse.<List<SchoolResponse>>builder()
                .data(schoolService.getAll(page, perPage))
                .build();
    }

    @PostMapping
    public ApiResponse<SchoolResponse> add(@RequestBody SchoolRequest request) {
        return ApiResponse.<SchoolResponse>builder()
                .data(schoolService.create(request))
                .build();
    }

    @GetMapping("/{schoolId}")
    public ApiResponse<SchoolResponse> getSchoolById(@PathVariable("schoolId") String schoolId) {
        return ApiResponse.<SchoolResponse>builder()
                .data(schoolService.getById(schoolId))
                .build();
    }

    @PatchMapping("/{schoolId}")
    public ApiResponse<SchoolResponse> update(@PathVariable("schoolId") String schoolId, @RequestBody SchoolRequest request) {
        return ApiResponse.<SchoolResponse>builder()
                .data(schoolService.update(schoolId, request))
                .build();
    }

    @DeleteMapping("/{schoolId}")
    public ApiResponse<Void> delete(@PathVariable("schoolId") String schoolId) {
        return ApiResponse.<Void>builder()
                .message(schoolService.delete(schoolId))
                .build();
    }
}
