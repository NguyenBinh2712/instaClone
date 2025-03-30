package com.example.instagram.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {
    private Long userId;
    private String content;
    private NotificationType type;


    public enum NotificationType {
        LIKE,
        COMMENT,
        FOLLOW,
        MENTION,
        MESSAGE
    }
}
