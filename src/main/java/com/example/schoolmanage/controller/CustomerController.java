package com.example.schoolmanage.controller;

import com.example.schoolmanage.dto.ApiResponse;
import com.example.schoolmanage.dto.request.CustomerRequest;
import com.example.schoolmanage.dto.response.CustomerResponse;
import com.example.schoolmanage.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ApiResponse<Void> addCustomer(@RequestBody CustomerRequest customerRequest) {
        log.info("customer controller");
        return ApiResponse.<Void>builder()
                .message(customerService.create(customerRequest))
                .build();
    }

    @GetMapping("/get-list-admins-by-school")
    public ApiResponse<List<CustomerResponse>> getAllCustomersBySchoolId(@RequestParam long schoolId, @RequestParam int page, @RequestParam int perPage) {
        return ApiResponse.<List<CustomerResponse>>builder()
                .data(customerService.getListAdminSchoolBySchoolId(schoolId, page, perPage))
                .build();
    }

    @DeleteMapping("/delete-from-school")
    public ApiResponse<Void> deleteCustomer(@RequestParam long schoolId, @RequestParam long customerId) {
        customerService.deleteCustomerInSchool(schoolId, customerId);
        return ApiResponse.<Void>builder()
                .message("Customer has been deleted!")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomers(@RequestParam int page, @RequestParam int perPage) {
        List<CustomerResponse> customerResponses = customerService.getListCustomer(page, perPage);
        long total = customerResponses.size();
        return ApiResponse.<List<CustomerResponse>>builder()
                .data(customerResponses)
                .total(total)
                .build();
    }

    @GetMapping("/{customerId}")
    public ApiResponse<CustomerResponse> getCustomer(@PathVariable long customerId) {
        return ApiResponse.<CustomerResponse>builder()
                .data(customerService.getCustomer(customerId))
                .build();
    }

    @PatchMapping("/{customerId}")
    public ApiResponse<Void> updateCustomer(@PathVariable long customerId, @RequestBody CustomerRequest customerRequest) {
        customerService.update(customerId, customerRequest);
        return ApiResponse.<Void>builder()
                .message("Customer has been updated!")
                .build();
    }
}
