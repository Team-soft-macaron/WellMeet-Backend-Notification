package com.wellmeet.notification.domain;

import com.wellmeet.notification.consumer.dto.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEnabled {

    @Id
    private String userId;

    @Enumerated(value = EnumType.STRING)
    private NotificationType type;

    @Enumerated(value = EnumType.STRING)
    private NotificationChannel channel;

    private boolean enabled;
}
