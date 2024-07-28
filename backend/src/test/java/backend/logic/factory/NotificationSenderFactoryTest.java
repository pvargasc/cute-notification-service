package backend.logic.factory;

import backend.logic.strategy.EmailNotificationSender;
import backend.logic.strategy.NotificationSender;
import backend.logic.strategy.PushNotificationSender;
import backend.logic.strategy.SMSNotificationSender;
import backend.shared.ChannelType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationSenderFactoryTest {

    @Configuration
    static class TestConfig {

        @Bean
        public SMSNotificationSender smsNotificationSender() {
            return Mockito.mock(SMSNotificationSender.class);
        }

        @Bean
        public EmailNotificationSender emailNotificationSender() {
            return Mockito.mock(EmailNotificationSender.class);
        }

        @Bean
        public PushNotificationSender pushNotificationSender() {
            return Mockito.mock(PushNotificationSender.class);
        }

        @Bean
        public NotificationSenderFactory notificationSenderFactory(
                Map<ChannelType, NotificationSender> notificationSenders
        ) {
            return new NotificationSenderFactory(notificationSenders);
        }

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

    @Autowired
    private NotificationSenderFactory notificationSenderFactory;

    @Autowired
    private SMSNotificationSender smsNotificationSender;

    @Autowired
    private EmailNotificationSender emailNotificationSender;

    @Autowired
    private PushNotificationSender pushNotificationSender;

    @Test
    public void testGetNotificationSender_SMS() {
        NotificationSender sender = notificationSenderFactory.getNotificationSender(ChannelType.SMS);
        assertNotNull(sender);
        assertEquals(smsNotificationSender, sender);
    }

    @Test
    public void testGetNotificationSender_EMAIL() {
        NotificationSender sender = notificationSenderFactory.getNotificationSender(ChannelType.EMAIL);
        assertNotNull(sender);
        assertEquals(emailNotificationSender, sender);
    }

    @Test
    public void testGetNotificationSender_PUSH_NOTIFICATION() {
        NotificationSender sender = notificationSenderFactory.getNotificationSender(ChannelType.PUSH_NOTIFICATION);
        assertNotNull(sender);
        assertEquals(pushNotificationSender, sender);
    }
}
