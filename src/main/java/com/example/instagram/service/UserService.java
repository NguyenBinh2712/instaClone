package com.example.instagram.service;

import com.example.instagram.dto.request.UserRequestDTO;
import com.example.instagram.dto.response.UserResponseDTO;
import com.example.instagram.entity.User;
import com.example.instagram.exception.AppException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);

    //register
    @Transactional
    public UserResponseDTO register(UserRequestDTO request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("USER EXISTED");
        }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        return convertToDTO(userRepository.save(user));
    }

    //login


    // converto sang DTO
    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        return dto;
    }
}
