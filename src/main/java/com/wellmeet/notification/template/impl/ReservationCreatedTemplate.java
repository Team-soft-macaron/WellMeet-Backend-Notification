package com.wellmeet.notification.template.impl;

import com.wellmeet.notification.consumer.dto.NotificationType;
import com.wellmeet.notification.template.NotificationTemplate;
import com.wellmeet.notification.template.NotificationTemplateData;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReservationCreatedTemplate implements NotificationTemplate {

    @Override
    public boolean supports(NotificationType type) {
        return NotificationType.RESERVATION_CREATED == type;
    }

    @Override
    public NotificationTemplateData create(Map<String, Object> payload) {
        String restaurantName = getStringOrDefault(payload, "restaurantName", "식당");
        String reservationTime = getStringOrDefault(payload, "reservationTime", "");
        String reservationId = getStringOrDefault(payload, "reservationId", "");

        String title = "새로운 예약이 접수되었습니다";
        String body = String.format("%s에 새로운 예약이 접수되었습니다. 예약 시간: %s",
                restaurantName, reservationTime);
        String url = "/reservations/" + reservationId;

        return new NotificationTemplateData(title, body, url, true);
    }

    private String getStringOrDefault(Map<String, Object> payload, String key, String defaultValue) {
        Object value = payload.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}
