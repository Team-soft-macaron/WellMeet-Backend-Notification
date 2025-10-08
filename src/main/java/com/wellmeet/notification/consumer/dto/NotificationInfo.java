package com.wellmeet.notification.consumer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationInfo {

    private NotificationType type;
    private String recipient;

    public NotificationInfo(NotificationType type, String recipient) {
        this.type = type;
        this.recipient = recipient;
    }
}
