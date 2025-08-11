package com.wellmeet.webpush.dto;

import java.util.Map;

public record TestPushRequest(
        String title,
        String body,
        Map<String, Object> data
) {
}
