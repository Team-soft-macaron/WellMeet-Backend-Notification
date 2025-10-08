package com.wellmeet.notification.template.impl;

import com.wellmeet.notification.consumer.dto.NotificationType;
import com.wellmeet.notification.template.NotificationTemplate;
import com.wellmeet.notification.template.NotificationTemplateData;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReservationReminderTemplate implements NotificationTemplate {

    @Override
    public boolean supports(NotificationType type) {
        return NotificationType.RESERVATION_REMINDER == type;
    }

    @Override
    public NotificationTemplateData create(Map<String, Object> payload) {
        String restaurantName = getStringOrDefault(payload, "restaurantName", "식당");
        String reservationTime = getStringOrDefault(payload, "reservationTime", "");
        String reservationId = getStringOrDefault(payload, "reservationId", "");
        String minutesBefore = getStringOrDefault(payload, "minutesBefore", "30");

        String title = "곧 예약 시간입니다";
        String body = String.format("%s 예약 시간이 %s분 남았습니다. 예약 시간: %s",
                restaurantName, minutesBefore, reservationTime);
        String url = "/reservations/" + reservationId;

        return new NotificationTemplateData(title, body, url, true);
    }
}
