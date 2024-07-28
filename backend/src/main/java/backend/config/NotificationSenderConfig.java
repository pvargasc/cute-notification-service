package backend.config;

import backend.logic.strategy.EmailNotificationSender;
import backend.logic.strategy.NotificationSender;
import backend.logic.strategy.PushNotificationSender;
import backend.logic.strategy.SMSNotificationSender;
import backend.shared.ChannelType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class NotificationSenderConfig {

    @Bean
    public Map<ChannelType, NotificationSender> notificationSenders(
            SMSNotificationSender smsNotificationSender,
            EmailNotificationSender emailNotificationSender,
            PushNotificationSender pushNotificationSender
    ) {
        return Map.of(
                ChannelType.SMS, smsNotificationSender,
                ChannelType.EMAIL, emailNotificationSender,
                ChannelType.PUSH_NOTIFICATION, pushNotificationSender
        );
    }
}

