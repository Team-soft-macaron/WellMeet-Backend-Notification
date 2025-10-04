package com.wellmeet.notification;

import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.domain.NotificationChannel;

public interface Sender {

    boolean isEnabled(NotificationChannel channel);

    void send(NotificationMessage message);
}
