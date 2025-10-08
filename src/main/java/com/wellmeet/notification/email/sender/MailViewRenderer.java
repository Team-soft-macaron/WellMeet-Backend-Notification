package com.wellmeet.notification.email.sender;

import com.wellmeet.exception.ErrorCode;
import com.wellmeet.exception.WellMeetNotificationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class MailViewRenderer {

    private static final String TEMPLATE_BASE_PATH = "templates/";

    public String render(String templateName, Map<String, String> variables) {
        String template = loadTemplate(templateName);
        return replacePlaceholders(template, variables);
    }

    private String loadTemplate(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource(TEMPLATE_BASE_PATH + templateName);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new WellMeetNotificationException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    private String replacePlaceholders(String template, Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
