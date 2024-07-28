package backend.logic.strategy;

import backend.amqp.AMQPPublisher;
import backend.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender {

    private final AMQPPublisher publisher;

    public EmailNotificationSender(AMQPPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendNotification(Notification notification) {
        // Any preliminary actions expected to happen before publishing the RabbitMQ message go here.
        // assembling a JSON object with more relevant data for the consumer.

        // A message is sent to the EMAIL queue so a dedicated Email Sender Service builds and delivers it.
        publisher.publish(
                notification.getCategory().getType().toString(),
                notification.getChannel().getType().toString(),
                notification.getMessage()
        );

        // Any follow-up actions expected to happen after publishing the RabbitMQ message go here.
    }
}
