package com.wellmeet.config;

import static org.mockito.Mockito.mock;

import nl.martijndwars.webpush.PushService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class WebPushTestConfig {

    @Bean
    @Primary
    public PushService pushService() {
        return mock(PushService.class);
    }
}
