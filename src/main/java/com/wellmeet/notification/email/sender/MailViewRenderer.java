package com.wellmeet.notification.email.sender;

import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Slf4j
@Component
public class MailViewRenderer {

    private static final String TEMPLATE_BASE_PATH = "templates/";
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    public String render(String templateName, Map<String, String> variables) {
        String template = loadTemplate(templateName);
        return replacePlaceholders(template, variables);
    }

    private String loadTemplate(String templateName) {
        ClassPathResource resource = new ClassPathResource(TEMPLATE_BASE_PATH + templateName);
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("이메일 템플릿 로딩에 실패했습니다. templateName: {}", templateName, e);
            throw new WellMeetNotificationException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    private String replacePlaceholders(String template, Map<String, String> variables) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = variables.getOrDefault(key, matcher.group(0));
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
