package com.wellmeet.notification.consumer;

import com.wellmeet.notification.consumer.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consume(NotificationMessage message) {
        log.info("Received message from notification topic: {}", message);
    }
}
