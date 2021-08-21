package dev.abelab.rippy.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.*;

@Data
@Configuration
@ConfigurationProperties("slack")
public class SlackProperty {

    /**
     * enabled
     */
    boolean enabled;

    /**
     * Webhook URL
     */
    String webhookUrl;

}
