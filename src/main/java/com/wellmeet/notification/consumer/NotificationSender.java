package com.wellmeet.notification.consumer;

import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.repository.NotificationHistoryRepository;
import com.wellmeet.notification.webpush.WebPushService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private final WebPushService webPushService;
    private final NotificationHistoryRepository notificationHistoryRepository;

    public void send(NotificationMessage payload, List<String> enabled) {

    }
}
