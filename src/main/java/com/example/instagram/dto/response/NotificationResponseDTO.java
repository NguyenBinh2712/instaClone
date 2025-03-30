package com.example.instagram.dto.response;

import com.example.instagram.dto.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
    private Long id;
    private UserDTO user;
    private String content;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime createdAt;

    public enum NotificationType {
        LIKE,
        COMMENT,
        FOLLOW,
        MENTION,
        MESSAGE
    }
}
