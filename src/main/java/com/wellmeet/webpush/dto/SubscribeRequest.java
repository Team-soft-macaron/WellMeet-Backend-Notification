package com.wellmeet.webpush.dto;

import com.wellmeet.webpush.domain.PushSubscription;

public record SubscribeRequest(
        String endpoint,
        Keys keys
) {

    public PushSubscription toDomain(Long userId) {
        return new PushSubscription(
                userId,
                endpoint,
                keys.p256dh(),
                keys.auth()
        );
    }

    public String p256dh() {
        return keys.p256dh();
    }

    public String auth() {
        return keys.auth();
    }

    public record Keys(
            String p256dh,
            String auth
    ) {
    }
}
