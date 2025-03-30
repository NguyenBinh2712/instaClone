package com.example.instagram.dto.response;

import com.example.instagram.dto.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Long id;
    private UserDTO user;
    private String content;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;
    private int commentsCount;
    private boolean isLiked;
}
