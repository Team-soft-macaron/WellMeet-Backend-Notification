package com.wellmeet.notification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNotificationEnabled {

    @Id
    private String userId;
    private boolean reservationConfirmWebPushEnabled;
    private boolean reservationConfirmEmailEnabled;
    private boolean reservationCancelWebPushEnabled;
    private boolean reservationCancelEmailEnabled;
    private boolean reminderWebPushEnabled;
    private boolean reminderEmailEnabled;
    private boolean promotionWebPushEnabled;
    private boolean promotionEmailEnabled;
}
