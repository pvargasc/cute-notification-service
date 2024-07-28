package backend.logic.factory;

import backend.logic.strategy.EmailNotificationSender;
import backend.logic.strategy.NotificationSender;
import backend.logic.strategy.PushNotificationSender;
import backend.logic.strategy.SMSNotificationSender;
import backend.shared.ChannelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationSenderFactory {

    private final Map<ChannelType, NotificationSender> notificationSenders;

    @Autowired
    public NotificationSenderFactory(Map<ChannelType, NotificationSender> notificationSenders) {
        this.notificationSenders = notificationSenders;
    }

    public NotificationSender getNotificationSender(ChannelType channelType) {
        final NotificationSender notificationSender = notificationSenders.get(channelType);

        if (notificationSender == null) {
            throw new IllegalArgumentException("Channel " + channelType + " does not exist or is deactivated.");
        }

        return notificationSender;
    }
}
