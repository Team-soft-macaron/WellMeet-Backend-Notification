package com.wellmeet.notification.consumer.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageHeader {

    private String messageId;
    private LocalDateTime timestamp;
}
