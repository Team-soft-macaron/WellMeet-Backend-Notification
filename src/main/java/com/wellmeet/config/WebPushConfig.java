package com.wellmeet.config;

import java.security.GeneralSecurityException;
import java.security.Security;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class WebPushConfig {

    private final VapidConfig vapidConfig;

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        PushService pushService = new PushService();
        pushService.setPublicKey(vapidConfig.getPublicKey());
        pushService.setPrivateKey(vapidConfig.getPrivateKey());
        pushService.setSubject(vapidConfig.getSubject());
        return pushService;
    }
}
