package com.wellmeet.notification.template;

import com.wellmeet.notification.consumer.dto.NotificationType;
import java.util.Map;

public interface NotificationTemplate {

    boolean supports(NotificationType type);

    NotificationTemplateData create(Map<String, Object> payload);
}
