package com.wellmeet.notification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerNotificationEnabled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerId;
    private boolean reservationRequestWebPushEnabled;
    private boolean reservationRequestEmailEnabled;
    private boolean reservationCancelWebPushEnabled;
    private boolean reservationCancelEmailEnabled;
    private boolean promotionWebPushEnabled;
    private boolean promotionEmailEnabled;
}
