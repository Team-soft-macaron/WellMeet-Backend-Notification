package com.wellmeet.webpush;

import com.wellmeet.webpush.dto.SubscribeRequest;
import com.wellmeet.webpush.dto.SubscribeResponse;
import com.wellmeet.webpush.dto.TestPushRequest;
import com.wellmeet.webpush.dto.UnsubscribeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notification")
@RestController
@RequiredArgsConstructor
public class WebPushController {

    private final WebPushService webPushService;

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscribeResponse subscribe(
            @RequestBody SubscribeRequest subscribeRequest,
            @RequestParam Long userId
    ) {
        return webPushService.subscribe(userId, subscribeRequest);
    }

    @PostMapping("/test-push")
    public void testPush(
            @RequestBody TestPushRequest testPushRequest,
            @RequestParam Long userId
    ) {
        webPushService.sendTestPush(userId, testPushRequest);
    }

    @DeleteMapping("/unsubscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(
            @RequestParam Long userId,
            @RequestBody UnsubscribeRequest request
    ) {
        webPushService.unsubscribe(userId, request);
    }
}
