package com.wellmeet.notification.webpush;

import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.webpush.domain.PushSubscription;
import com.wellmeet.notification.webpush.dto.SubscribeRequest;
import com.wellmeet.notification.webpush.dto.SubscribeResponse;
import com.wellmeet.notification.webpush.dto.TestPushRequest;
import com.wellmeet.notification.webpush.dto.UnsubscribeRequest;
import com.wellmeet.notification.webpush.infrastructure.WebPushSender;
import com.wellmeet.notification.webpush.repository.PushSubscriptionRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebPushService {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final WebPushSender webPushSender;

    @Transactional
    public SubscribeResponse subscribe(String userId, SubscribeRequest request) {
        List<PushSubscription> existingSubscriptions = pushSubscriptionRepository.findByUserId(userId);
        Optional<PushSubscription> pushSubscription = existingSubscriptions.stream()
                .filter(subscription -> subscription.isSameEndpoint(request.endpoint()))
                .findAny();

        if (pushSubscription.isPresent()) {
            PushSubscription subscription = pushSubscription.get();
            subscription.update(request.toDomain(userId));
            return new SubscribeResponse(subscription);
        }

        PushSubscription subscription = request.toDomain(userId);
        PushSubscription savedSubscription = pushSubscriptionRepository.save(subscription);
        return new SubscribeResponse(savedSubscription);
    }

    public void sendTestPush(String userId, TestPushRequest request) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUserId(userId);
        if (subscriptions.isEmpty()) {
            throw new WellMeetNotificationException(ErrorCode.SUBSCRIPTION_NOT_FOUND);
        }

        subscriptions.forEach(subscription -> webPushSender.send(subscription, request));
    }

    @Transactional
    public void unsubscribe(String userId, UnsubscribeRequest request) {
        if (!pushSubscriptionRepository.existsByUserIdAndEndpoint(userId, request.endpoint())) {
            throw new WellMeetNotificationException(ErrorCode.SUBSCRIPTION_NOT_FOUND);
        }
        pushSubscriptionRepository.deleteByUserIdAndEndpoint(userId, request.endpoint());
    }
}
