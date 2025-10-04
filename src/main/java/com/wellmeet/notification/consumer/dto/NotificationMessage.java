package com.wellmeet.notification.consumer.dto;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationMessage {

    private MessageHeader header;
    private NotificationInfo notification;
    private Map<String, Object> payload;

    public String getRecipient() {
        return notification.getRecipient();
    }
}
