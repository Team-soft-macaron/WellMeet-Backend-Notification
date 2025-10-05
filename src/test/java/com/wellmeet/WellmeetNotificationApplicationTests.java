package com.wellmeet;

import com.wellmeet.config.WebPushTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(WebPushTestConfig.class)
class WellmeetNotificationApplicationTests {

    @Test
    void contextLoads() {
    }
}
