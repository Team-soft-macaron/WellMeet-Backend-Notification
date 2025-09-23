package com.wellmeet.notification.consumer.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationCreatedPayload extends NotificationPayload {

    private Long reservationId;
    private String customerName;
    private LocalDateTime reservationTime;
    private int partySize;
}
