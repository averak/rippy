package dev.abelab.rippy.client;

import org.springframework.stereotype.Component;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import dev.abelab.rippy.property.SlackProperty;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.InternalServerErrorException;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackClient {

    private final Slack slack = Slack.getInstance();

    private final SlackProperty slackProperty;

    /**
     * メッセージ送信
     *
     * @param message 送信メッセージ
     */
    public void sendMessage(String message) {
        if (!this.slackProperty.isEnabled()) {
            return;
        }

        final var payload = Payload.builder().text(message).build();
        try {
            final var response = this.slack.send(this.slackProperty.getWebhookUrl(), payload);
            log.info(response.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException(ErrorCode.FAILED_TO_SEND_SLACK);
        }
    }

}
