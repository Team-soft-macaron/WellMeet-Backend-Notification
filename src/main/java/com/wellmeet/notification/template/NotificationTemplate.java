package com.wellmeet.notification.template;

import com.wellmeet.notification.consumer.dto.NotificationType;
import java.util.Map;
import java.util.Objects;

public interface NotificationTemplate {

    boolean supports(NotificationType type);

    NotificationTemplateData create(Map<String, Object> payload);

    default String getStringOrDefault(Map<String, Object> payload, String key, String defaultValue) {
        Object value = payload.get(key);
        return Objects.toString(value, defaultValue);
    }
}
