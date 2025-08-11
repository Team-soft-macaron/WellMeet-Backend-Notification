package com.wellmeet.exception;

import lombok.Getter;

@Getter
public class WellMeetNotificationException extends RuntimeException {

    private final int statusCode;

    public WellMeetNotificationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
    }
}
