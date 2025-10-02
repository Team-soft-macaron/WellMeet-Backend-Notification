package com.wellmeet.notification.consumer;

import com.wellmeet.notification.repository.NotificationEnabledRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationChecker {

    private final NotificationEnabledRepository notificationEnabledRepository;

    public List<String> check(String recipient, String recipientType) {
        return null;
    }
}
