package backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "categories-exchange";

    public static final String QUEUE_EMAIL = "email-queue";
    public static final String QUEUE_SMS = "sms-queue";
    public static final String QUEUE_PUSH_NOTIFICATION = "push-notification-queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS);
    }

    @Bean
    public Queue pushNotificationQueue() {
        return new Queue(QUEUE_PUSH_NOTIFICATION);
    }

    @Bean
    public Binding bindingEmail(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue).to(topicExchange).with("*.EMAIL");
    }

    @Bean
    public Binding bindingSMS(Queue smsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(smsQueue).to(topicExchange).with("*.SMS");
    }

    @Bean
    public Binding bindingPushNotification(Queue pushNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(pushNotificationQueue).to(topicExchange).with("*.PUSH_NOTIFICATION");
    }

    /*@Bean
    public Binding bindingSportsEmail(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue).to(topicExchange).with("SPORTS.EMAIL");
    }

    @Bean
    public Binding bindingSportsSMS(Queue smsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(smsQueue).to(topicExchange).with("SPORTS.SMS");
    }

    @Bean
    public Binding bindingSportsPushNotification(Queue pushNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(pushNotificationQueue).to(topicExchange).with("SPORTS.PUSH_NOTIFICATION");
    }


    @Bean
    public Binding bindingFinanceEmail(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue).to(topicExchange).with("FINANCE.EMAIL");
    }

    @Bean
    public Binding bindingFinanceSMS(Queue smsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(smsQueue).to(topicExchange).with("FINANCE.SMS");
    }

    @Bean
    public Binding bindingFinancePushNotification(Queue pushNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(pushNotificationQueue).to(topicExchange).with("FINANCE.PUSH_NOTIFICATION");
    }


    @Bean
    public Binding bindingMoviesEmail(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue).to(topicExchange).with("MOVIES.EMAIL");
    }

    @Bean
    public Binding bindingMoviesSMS(Queue smsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(smsQueue).to(topicExchange).with("MOVIES.SMS");
    }

    @Bean
    public Binding bindingMoviesPushNotification(Queue pushNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(pushNotificationQueue).to(topicExchange).with("MOVIES.PUSH_NOTIFICATION");
    }*/
//
//    @Bean
//    public Binding bindingPushNotification(Queue pushNotificationQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(pushNotificationQueue).to(topicExchange).with("*.push.*");
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//
//    @Bean
//    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        return factory;
//    }
}

