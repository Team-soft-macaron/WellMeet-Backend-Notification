package com.wellmeet;

import com.wellmeet.config.EmailTestConfig;
import com.wellmeet.config.WebPushTestConfig;
import com.wellmeet.notification.webpush.repository.PushSubscriptionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(DataBaseCleaner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import({WebPushTestConfig.class, EmailTestConfig.class})
public abstract class BaseServiceTest {

    @Autowired
    protected PushSubscriptionRepository pushSubscriptionRepository;
}
