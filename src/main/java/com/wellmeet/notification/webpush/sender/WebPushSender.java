package com.wellmeet.notification.webpush.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.Sender;
import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.domain.NotificationChannel;
import com.wellmeet.notification.template.NotificationTemplateData;
import com.wellmeet.notification.template.NotificationTemplateFactory;
import com.wellmeet.notification.webpush.domain.PushSubscription;
import com.wellmeet.notification.webpush.repository.PushSubscriptionRepository;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Subscription.Keys;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebPushSender implements Sender {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final PushService pushService;
    private final NotificationTemplateFactory templateFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isEnabled(NotificationChannel channel) {
        return NotificationChannel.WEB_PUSH == channel;
    }

    @Override
    public void send(NotificationMessage message) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUserId(
                message.getNotification().getRecipient());
        if (subscriptions.isEmpty()) {
            throw new WellMeetNotificationException(ErrorCode.SUBSCRIPTION_NOT_FOUND);
        }

        subscriptions.forEach(subscription -> {
            Keys keys = new Keys(subscription.getP256dh(), subscription.getAuth());
            Subscription sub = new Subscription(subscription.getEndpoint(), keys);
            Map<String, Object> notificationPayload = getNotificationPayload(message);
            webPushSend(notificationPayload, sub);
        });
    }

    private Map<String, Object> getNotificationPayload(NotificationMessage message) {
        NotificationTemplateData templateData = templateFactory.createTemplateData(
                message.getNotification().getType(),
                message.getPayload()
        );

        Map<String, Object> notificationPayload = new HashMap<>();
        notificationPayload.put("title", templateData.title());
        notificationPayload.put("body", templateData.body());
        notificationPayload.put("icon", "/icon-192x192.png");
        notificationPayload.put("badge", "/badge-72x72.png");
        notificationPayload.put("vibrate", new int[]{100, 50, 100});
        notificationPayload.put("requireInteraction", templateData.requireInteraction());

        Map<String, Object> defaultData = new HashMap<>();
        defaultData.put("url", templateData.url());
        defaultData.put("timestamp", System.currentTimeMillis());
        notificationPayload.put("data", defaultData);
        return notificationPayload;
    }

    private void webPushSend(Map<String, Object> notificationPayload, Subscription sub) {
        try {
            String payloadJson = objectMapper.writeValueAsString(notificationPayload);
            Notification notification = new Notification(sub, payloadJson);
            pushService.send(notification);
        } catch (JoseException | GeneralSecurityException | IOException | ExecutionException | InterruptedException e) {
            throw new WellMeetNotificationException(ErrorCode.WEB_PUSH_SEND_FAILED);
        }
    }
}
