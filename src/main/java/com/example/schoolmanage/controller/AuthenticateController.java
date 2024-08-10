package com.example.schoolmanage.controller;

import com.example.schoolmanage.dto.ApiResponse;
import com.example.schoolmanage.dto.request.AuthenticateRequest;
import com.example.schoolmanage.dto.request.TokenRequest;
import com.example.schoolmanage.dto.response.AuthenticateResponse;
import com.example.schoolmanage.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticateController {
    @Autowired
    AuthenticateService authenticateService;

    @PostMapping("/login")
    public ApiResponse<AuthenticateResponse> login(@RequestBody AuthenticateRequest authenticateRequest)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticateResponse>builder()
                .data(authenticateService.login(authenticateRequest))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody AuthenticateRequest authenticateRequest)
            throws ParseException, JOSEException {
        return ApiResponse.<Void>builder()
                .message(authenticateService.register((authenticateRequest)))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws ParseException, JOSEException {
        return ApiResponse.<Void>builder()
                .message(authenticateService.logout())
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<String> refreshToken(@RequestBody TokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<String>builder()
                .data(authenticateService.refreshToken(request))
                .build();
    }
}
