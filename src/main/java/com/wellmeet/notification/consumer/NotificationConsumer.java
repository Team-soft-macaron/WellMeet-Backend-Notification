package com.wellmeet.notification.consumer;

import com.wellmeet.notification.consumer.dto.NotificationMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationChecker notificationChecker;
    private final NotificationSender notificationSender;

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consume(NotificationMessage message) {
        List<String> enabled = notificationChecker.check(message.getNotification().getRecipient(),
                message.getNotification().getRecipientType());
        notificationSender.send(message, enabled);
    }
}
