package com.wellmeet.notification.consumer;

import com.wellmeet.notification.repository.OwnerNotificationEnabledRepository;
import com.wellmeet.notification.repository.UserNotificationEnabledRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationChecker {

    private final OwnerNotificationEnabledRepository ownerNotificationEnabledRepository;
    private final UserNotificationEnabledRepository userNotificationEnabledRepository;

    public List<String> check(String recipient, String recipientType) {
        return null;
    }
}
