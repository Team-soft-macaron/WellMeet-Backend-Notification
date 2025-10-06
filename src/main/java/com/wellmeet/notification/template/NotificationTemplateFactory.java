package com.wellmeet.notification.template;

import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.consumer.dto.NotificationType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationTemplateFactory {

    private final List<NotificationTemplate> templates;

    public NotificationTemplateData createTemplateData(NotificationType type, Map<String, Object> payload) {
        NotificationTemplate template = templates.stream()
                .filter(low -> low.supports(type))
                .findFirst()
                .orElseThrow(() -> new WellMeetNotificationException(ErrorCode.TEMPLATE_NOT_FOUND));

        return template.create(payload);
    }
}
