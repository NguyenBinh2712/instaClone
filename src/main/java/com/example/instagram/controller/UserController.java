package com.example.instagram.controller;

import com.example.instagram.dto.request.UserRequestDTO;
import com.example.instagram.dto.response.ApiResponse;
import com.example.instagram.dto.response.UserResponseDTO;
import com.example.instagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getUser(){
        var authenticated= SecurityContextHolder.getContext().getAuthentication();
        log.info("username:{}",authenticated.getName());
        authenticated.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponseDTO>>builder()
                .result(userService.getUser())
                .build();
    }
}
