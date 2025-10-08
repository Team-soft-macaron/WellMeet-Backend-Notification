package com.wellmeet.notification.template;

public record NotificationTemplateData(
        String title,
        String body,
        String url,
        boolean requireInteraction
) {
}
