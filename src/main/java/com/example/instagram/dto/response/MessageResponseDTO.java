package com.example.instagram.dto.response;

import com.example.instagram.dto.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private Long id;
    private UserDTO sender;
    private UserDTO receiver;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;
}
