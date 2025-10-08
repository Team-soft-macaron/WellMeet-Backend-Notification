package com.wellmeet.notification.email.sender;

import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.Sender;
import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.domain.NotificationChannel;
import com.wellmeet.notification.email.domain.EmailSubscription;
import com.wellmeet.notification.email.repository.EmailSubscriptionRepository;
import com.wellmeet.notification.template.NotificationTemplateData;
import com.wellmeet.notification.template.NotificationTemplateFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender implements Sender {

    private final EmailSubscriptionRepository emailSubscriptionRepository;
    private final NotificationTemplateFactory templateFactory;
    private final MailViewRenderer mailViewRenderer;
    private final MailTransport mailTransport;

    @Override
    public boolean isEnabled(NotificationChannel channel) {
        return NotificationChannel.EMAIL == channel;
    }

    @Override
    public void send(NotificationMessage message) {
        String userId = message.getNotification().getRecipient();
        EmailSubscription subscription = emailSubscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new WellMeetNotificationException(ErrorCode.EMAIL_NOT_FOUND));

        NotificationTemplateData templateData = templateFactory.createTemplateData(
                message.getNotification().getType(),
                message.getPayload()
        );

        String subject = templateData.title();
        String htmlContent = buildHtmlContent(templateData);
        sendEmail(subscription.getEmail(), subject, htmlContent);
    }

    private String buildHtmlContent(NotificationTemplateData templateData) {
        Map<String, String> variables = Map.of(
                "title", templateData.title(),
                "body", templateData.body(),
                "url", templateData.url()
        );
        return mailViewRenderer.render("email-notification.html", variables);
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        mailTransport.send(to, subject, htmlContent);
    }
}
