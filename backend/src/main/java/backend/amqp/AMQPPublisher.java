package backend.amqp;

import backend.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AMQPPublisher {

    private final RabbitTemplate rabbitTemplate;

    public AMQPPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String category, String channel, String message) {
        final String routingKey = category + "." + channel;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
    }
}
