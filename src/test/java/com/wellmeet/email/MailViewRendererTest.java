package com.wellmeet.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wellmeet.exception.WellMeetNotificationException;
import com.wellmeet.notification.email.sender.MailViewRenderer;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MailViewRendererTest {

    @InjectMocks
    private MailViewRenderer mailViewRenderer;

    @Nested
    class Render {

        @Test
        void 플레이스홀더를_정상적으로_치환한다() {
            Map<String, String> variables = Map.of(
                    "title", "테스트 제목",
                    "body", "테스트 내용"
            );

            String result = mailViewRenderer.render("test-template.html", variables);

            assertThat(result).contains("테스트 제목")
                    .contains("테스트 내용")
                    .doesNotContain("{title}")
                    .doesNotContain("{body}");
        }

        @Test
        void 존재하지_않는_키는_원본_플레이스홀더를_유지한다() {
            Map<String, String> variables = Map.of(
                    "title", "테스트 제목",
                    "body", "테스트 내용"
            );

            String result = mailViewRenderer.render("test-template.html", variables);

            assertThat(result).contains("{unknown}");
        }

        @Test
        void 값에_특수문자가_포함되어도_안전하게_치환한다() {
            Map<String, String> variables = Map.of(
                    "content", "$100 & <script>alert('xss')</script>"
            );

            String result = mailViewRenderer.render("special-chars-template.html", variables);

            assertThat(result).contains("$100")
                    .contains("&")
                    .contains("<script>")
                    .doesNotContain("{content}");
        }

        @Test
        void 값에_플레이스홀더_형식이_포함되어도_재치환되지_않는다() {
            Map<String, String> variables = Map.of(
                    "first", "This is {second}",
                    "second", "should not appear"
            );

            String result = mailViewRenderer.render("nested-placeholder-template.html", variables);

            assertThat(result).contains("This is {second}")
                    .doesNotContain("should not appear");
        }

        @Test
        void 여러_플레이스홀더를_동시에_치환한다() {
            Map<String, String> variables = Map.of(
                    "title", "제목1",
                    "body", "내용1"
            );

            String result = mailViewRenderer.render("test-template.html", variables);

            assertThat(result).contains("제목1")
                    .contains("내용1");
        }

        @Test
        void 존재하지_않는_템플릿_파일을_로드하면_예외를_던진다() {
            Map<String, String> variables = Map.of("title", "제목");

            assertThatThrownBy(() -> mailViewRenderer.render("non-existent.html", variables))
                    .isInstanceOf(WellMeetNotificationException.class);
        }
    }
}
