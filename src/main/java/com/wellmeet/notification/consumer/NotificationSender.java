package com.wellmeet.notification.consumer;

import com.wellmeet.notification.Sender;
import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.domain.NotificationEnabled;
import com.wellmeet.notification.domain.NotificationHistory;
import com.wellmeet.notification.repository.NotificationHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    private final List<Sender> senders;
    private final NotificationHistoryRepository notificationHistoryRepository;

    public void send(NotificationMessage message, List<NotificationEnabled> enables) {
        for (NotificationEnabled enabled : enables) {
            Sender sender = senders.stream()
                    .filter(low -> low.isEnabled(enabled.getChannel()))
                    .findFirst()
                    .orElseThrow();
            sender.send(message);
            notificationHistoryRepository.save(new NotificationHistory(message.getNotification().getRecipient()));
        }
    }
}
