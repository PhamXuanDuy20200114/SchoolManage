package com.example.schoolmanage.service;

import com.example.schoolmanage.dto.request.SchoolRequest;
import com.example.schoolmanage.dto.response.SchoolResponse;
import com.example.schoolmanage.entity.School;
import com.example.schoolmanage.exception.AppException;
import com.example.schoolmanage.exception.ErrorCode;
import com.example.schoolmanage.repository.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SchoolService {
    @Autowired
    SchoolRepository schoolRepository;
    public List<SchoolResponse> getAll(int page, int perPage) {
        Page<School> schools =  schoolRepository.findAll(
                PageRequest.of(page, perPage)
        );
        log.info(schools.toString());
        List<SchoolResponse> schoolResponses =  schools.stream()
                .map(school -> SchoolResponse.builder()
                        .schoolId(school.getSchoolId())
                        .name(school.getName())
                        .address(school.getAddress())
                        .avt(school.getAvt())
                        .payment_from(school.getPayment_from())
                        .payment_to(school.getPayment_to())
                        .taxCode(school.getTaxCode())
                        .province(school.getProvince())
                        .district(school.getDistrict())
                        .commune(school.getCommune())
                        .build())
                .collect(Collectors.toList());
        return schoolResponses;
    }

    public SchoolResponse create(SchoolRequest request) {
        School school =  School.builder()
                .name(request.getName())
                .address(request.getAddress())
                .province(request.getProvince())
                .district(request.getDistrict())
                .commune(request.getCommune())
                .taxCode(request.getTaxCode())
                .schoolType(request.getSchoolType())
                .build();
        schoolRepository.save(school);
        return SchoolResponse.builder()
                .name(school.getName())
                .address(request.getAddress())
                .province(school.getProvince())
                .district(school.getDistrict())
                .commune(school.getCommune())
                .taxCode(school.getTaxCode())
                .schoolType(school.getSchoolType())
                .build();
    }

    public SchoolResponse getById(String id){
        School school = schoolRepository.findById(id).orElseThrow();
        return SchoolResponse.builder()
                .schoolId(school.getSchoolId())
                .name(school.getName())
                .address(school.getAddress())
                .province(school.getProvince())
                .district(school.getDistrict())
                .commune(school.getCommune())
                .taxCode(school.getTaxCode())
                .schoolType(school.getSchoolType())
                .build();
    }

    public SchoolResponse update(String id, SchoolRequest request) {
        School school = schoolRepository.findById(id).orElseThrow();
        school.setName(request.getName());
        school.setAddress(request.getAddress());
        school.setProvince(request.getProvince());
        school.setDistrict(request.getDistrict());
        school.setCommune(request.getCommune());
        school.setTaxCode(request.getTaxCode());
        school.setSchoolType(request.getSchoolType());
        schoolRepository.save(school);
        return SchoolResponse.builder()
                .schoolId(school.getSchoolId())
                .name(school.getName())
                .address(school.getAddress())
                .province(school.getProvince())
                .district(school.getDistrict())
                .commune(school.getCommune())
                .taxCode(school.getTaxCode())
                .schoolType(school.getSchoolType())
                .build();
    }

    public String delete(String id) {
        schoolRepository.deleteById(id);
        return "School has been deleted";
    }
}
