package com.wellmeet.notification.consumer.dto;

import lombok.Getter;

@Getter
public enum NotificationType {

    RESERVATION_CREATED,
    RESERVATION_CONFIRMED,
    RESERVATION_UPDATED,
    RESERVATION_CANCELED,
    RESERVATION_REMINDER,
}
