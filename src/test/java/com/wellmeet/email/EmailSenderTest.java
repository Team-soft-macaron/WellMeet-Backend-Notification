package com.wellmeet.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wellmeet.config.EmailTestConfig;
import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.consumer.dto.NotificationInfo;
import com.wellmeet.notification.consumer.dto.NotificationMessage;
import com.wellmeet.notification.consumer.dto.NotificationType;
import com.wellmeet.notification.domain.NotificationChannel;
import com.wellmeet.notification.email.domain.EmailSubscription;
import com.wellmeet.notification.email.repository.EmailSubscriptionRepository;
import com.wellmeet.notification.email.sender.EmailSender;
import com.wellmeet.notification.email.sender.MailTransport;
import com.wellmeet.notification.email.sender.MailViewRenderer;
import com.wellmeet.notification.template.NotificationTemplateData;
import com.wellmeet.notification.template.NotificationTemplateFactory;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Import(EmailTestConfig.class)
class EmailSenderTest {

    @Mock
    private EmailSubscriptionRepository emailSubscriptionRepository;

    @Mock
    private NotificationTemplateFactory templateFactory;

    @Mock
    private MailViewRenderer mailViewRenderer;

    @Mock
    private MailTransport mailTransport;

    @InjectMocks
    private EmailSender emailSender;

    @Nested
    class IsEnabled {

        @Test
        void EMAIL_채널이면_true를_반환한다() {
            boolean enabled = emailSender.isEnabled(NotificationChannel.EMAIL);

            assertThat(enabled).isTrue();

        }

        @Test
        void WEB_PUSH_채널이면_false를_반환한다() {
            boolean enabled = emailSender.isEnabled(NotificationChannel.WEB_PUSH);
            
            assertThat(enabled).isFalse();
        }
    }

    @Nested
    class Send {

        @Test
        void 이메일을_성공적으로_발송한다() {
            NotificationMessage notificationMessage = createNotificationMessage();
            NotificationTemplateData templateData = createTemplateData();
            EmailSubscription subscription = new EmailSubscription("user123", "test@example.com");

            when(emailSubscriptionRepository.findByUserId("user123")).thenReturn(Optional.of(subscription));
            when(templateFactory.createTemplateData(
                    NotificationType.RESERVATION_CREATED,
                    notificationMessage.getPayload()
            )).thenReturn(templateData);
            when(mailViewRenderer.render(any(), any())).thenReturn("<html>test</html>");
            doNothing().when(mailTransport).send(any(), any(), any());

            assertThatCode(() -> emailSender.send(notificationMessage))
                    .doesNotThrowAnyException();

            verify(mailTransport).send(any(), any(), any());
        }

        @Test
        void 이메일_발송_실패시_예외를_던진다() {
            NotificationMessage notificationMessage = createNotificationMessage();
            NotificationTemplateData templateData = createTemplateData();
            EmailSubscription subscription = new EmailSubscription("user123", "test@example.com");

            when(emailSubscriptionRepository.findByUserId("user123")).thenReturn(Optional.of(subscription));
            when(templateFactory.createTemplateData(
                    NotificationType.RESERVATION_CREATED,
                    notificationMessage.getPayload()
            )).thenReturn(templateData);
            when(mailViewRenderer.render(any(), any())).thenReturn("<html>test</html>");
            doThrow(new WellMeetNotificationException(ErrorCode.EMAIL_SEND_FAILED))
                    .when(mailTransport).send(any(), any(), any());

            assertThatThrownBy(() -> emailSender.send(notificationMessage))
                    .isInstanceOf(WellMeetNotificationException.class)
                    .hasMessageContaining(ErrorCode.EMAIL_SEND_FAILED.getMessage());
        }

        @Test
        void 이메일_구독_정보가_없으면_예외를_던진다() {
            NotificationMessage notificationMessage = createNotificationMessage();

            when(emailSubscriptionRepository.findByUserId("user123")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> emailSender.send(notificationMessage))
                    .isInstanceOf(WellMeetNotificationException.class)
                    .hasMessageContaining(ErrorCode.EMAIL_NOT_FOUND.getMessage());
        }
    }

    private NotificationMessage createNotificationMessage() {
        NotificationInfo notificationInfo = new NotificationInfo(
                NotificationType.RESERVATION_CREATED,
                "user123"
        );

        return new NotificationMessage(
                null,
                notificationInfo,
                Map.of(
                        "restaurantName", "테스트 식당",
                        "reservationTime", "2025-10-08 18:00",
                        "reservationId", "123"
                )
        );
    }

    private NotificationTemplateData createTemplateData() {
        return new NotificationTemplateData(
                "새로운 예약이 접수되었습니다",
                "테스트 식당에 새로운 예약이 접수되었습니다. 예약 시간: 2025-10-08 18:00",
                "/reservations/123",
                true
        );
    }
}
