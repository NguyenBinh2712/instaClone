package com.example.instagram.controller;

import com.example.instagram.dto.request.AuthRequest;
import com.example.instagram.dto.request.IntrospectRequest;
import com.example.instagram.dto.response.ApiResponse;
import com.example.instagram.dto.response.AuthResponse;
import com.example.instagram.dto.response.IntrospectResponse;
import com.example.instagram.service.AuthService;
import com.example.instagram.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/log-in")
    public ApiResponse<AuthResponse> authenticated(@RequestBody AuthRequest authRequest){
        var result=authService.authenticated(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result=authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
