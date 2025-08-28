package com.wellmeet.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consume(String message) {
        log.info("Received message from notification topic: {}", message);
    }
}
