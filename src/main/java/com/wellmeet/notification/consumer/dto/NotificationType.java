package com.wellmeet.notification.consumer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    RESERVATION_CREATED("reservation.created", "wellmeet-user-server");

    private final String name;
    private final String source;
}
