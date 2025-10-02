package com.wellmeet.notification;

import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.domain.NotificationChannel;

public interface Sender {

    public boolean isEnabled(NotificationChannel channel);

    public void send(NotificationMessage message);
}
