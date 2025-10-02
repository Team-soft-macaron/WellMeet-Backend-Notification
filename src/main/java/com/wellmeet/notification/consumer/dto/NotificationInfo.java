package com.wellmeet.notification.consumer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationInfo {

    private String type;
    private String category;
    private String recipient;
    private String recipientType;
}
