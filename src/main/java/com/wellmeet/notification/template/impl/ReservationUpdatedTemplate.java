package com.wellmeet.notification.template.impl;

import com.wellmeet.notification.consumer.dto.NotificationType;
import com.wellmeet.notification.template.NotificationTemplate;
import com.wellmeet.notification.template.NotificationTemplateData;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReservationUpdatedTemplate implements NotificationTemplate {

    @Override
    public boolean supports(NotificationType type) {
        return NotificationType.RESERVATION_UPDATED == type;
    }

    @Override
    public NotificationTemplateData create(Map<String, Object> payload) {
        String restaurantName = getStringOrDefault(payload, "restaurantName", "식당");
        String reservationTime = getStringOrDefault(payload, "reservationTime", "");
        String reservationId = getStringOrDefault(payload, "reservationId", "");

        String title = "예약 정보가 변경되었습니다";
        String body = String.format("%s 예약 정보가 변경되었습니다. 변경된 예약 시간: %s",
                restaurantName, reservationTime);
        String url = "/reservations/" + reservationId;

        return new NotificationTemplateData(title, body, url, false);
    }

    private String getStringOrDefault(Map<String, Object> payload, String key, String defaultValue) {
        Object value = payload.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}
