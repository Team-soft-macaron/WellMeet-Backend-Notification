package com.wellmeet.notification.consumer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationMessage {

    private MessageHeader header;
    private NotificationInfo notification;
    private NotificationPayload payload;
}
