package backend.service;

import backend.amqp.AMQPPublisher;
import backend.domain.Category;
import backend.domain.Channel;
import backend.domain.Notification;
import backend.domain.User;
import backend.dto.NotificationDTO;
import backend.dto.NotificationRequest;
import backend.logic.factory.NotificationSenderFactory;
import backend.logic.strategy.EmailNotificationSender;
import backend.logic.strategy.NotificationSender;
import backend.logic.strategy.PushNotificationSender;
import backend.logic.strategy.SMSNotificationSender;
import backend.repository.CategoryRepository;
import backend.repository.NotificationRepository;
import backend.repository.UserRepository;
import backend.shared.CategoryType;
import backend.shared.ChannelType;
import backend.shared.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotificationServiceTest {

    @SpyBean
    EmailNotificationSender emailNotificationSender;
    @SpyBean
    SMSNotificationSender smsNotificationSender;
    Category category1;
    Category category2;
    Channel channel1;
    Channel channel2;
    @Captor
    ArgumentCaptor<Notification> notificationArgumentCaptor;
    @SpyBean
    private NotificationRepository notificationRepository;
    @SpyBean
    private UserRepository userRepository;
    @SpyBean
    private CategoryRepository categoryRepository;
    @Autowired
    private NotificationService notificationService;
    private List<Notification> notificationList;
    private List<User> users;


    @BeforeEach
    public void setup() {
        ZonedDateTime now = ZonedDateTime.now();

        category1 = new Category(1L, CategoryType.MOVIES, Status.ACTIVE);
        category2 = new Category(2L, CategoryType.FINANCE, Status.ACTIVE);

        channel1 = new Channel(1L, ChannelType.EMAIL, Status.ACTIVE);
        channel2 = new Channel(2L, ChannelType.SMS, Status.ACTIVE);

        User user1 = new User(
                1L,
                "John",
                "Malkovich",
                "5555555555",
                "john@email.com",
                Status.ACTIVE,
                Set.of(category1, category2),
                // In order to guarantee the order for validation
                // a LinkedHashSet is used
                new LinkedHashSet<>(Set.of(channel1, channel2))
        );

        User user2 = new User(
                2L,
                "Sandra",
                "Bullock",
                "5555555555",
                "sandra@email.com",
                Status.ACTIVE,
                Set.of(category1),
                Set.of(channel2)
        );

        Notification notification1 = new Notification(
                1L,
                category1,
                channel1,
                user1,
                now,
                "message 1"
        );

        Notification notification2 = new Notification(
                2L,
                category2,
                channel2,
                user1,
                now,
                "message 2"
        );

        notificationList = List.of(notification1, notification2);
        users = List.of(user1, user2);

        when(notificationRepository.findAllByOrderBySentAtDesc()).thenReturn(notificationList);
        when(userRepository.findByCategory(category1)).thenReturn(users);
    }

    @AfterEach
    public void tearDown() {
        clearInvocations(notificationRepository);
        clearInvocations(userRepository);
        clearInvocations(categoryRepository);
    }

    @Test
    public void testFindAllNotifications() {
        List<NotificationDTO> notificationDTOList = notificationService.findAllNotifications();

        assertEquals(2, notificationDTOList.size());
        assertEquals("John Malkovich", notificationDTOList.get(0).getUserFullName());
        assertEquals("John Malkovich", notificationDTOList.get(1).getUserFullName());
        assertEquals(CategoryType.MOVIES.toString(), notificationDTOList.get(0).getCategory());
        assertEquals(CategoryType.FINANCE.toString(), notificationDTOList.get(1).getCategory());
        assertEquals("message 1", notificationDTOList.get(0).getMessage());
        assertEquals("message 2", notificationDTOList.get(1).getMessage());
    }

    @Test
    public void handleNotificationRequest() {
        when(categoryRepository.findByType(any(CategoryType.class))).thenReturn(category1);

        notificationService.handleNotificationRequest(new NotificationRequest("MOVIES", "Hello"));

        verify(categoryRepository).findByType(CategoryType.MOVIES);

        verify(smsNotificationSender, times(2)).sendNotification(notificationArgumentCaptor.capture());
        verify(emailNotificationSender, times(1)).sendNotification(notificationArgumentCaptor.capture());
        verify(notificationRepository, times(3)).save(notificationArgumentCaptor.capture());

        List<Notification> capturedNotifications = notificationArgumentCaptor.getAllValues();

        // User 1 has both SMS and Email channels
        assertEquals(category1, capturedNotifications.get(0).getCategory());
        assertEquals(channel2, capturedNotifications.get(0).getChannel());
        assertEquals(users.get(0), capturedNotifications.get(0).getUser());

        // User 2 only has SMS
        assertEquals(category1, capturedNotifications.get(1).getCategory());
        assertEquals(channel2, capturedNotifications.get(1).getChannel());
        assertEquals(users.get(1), capturedNotifications.get(1).getUser());

        // User 1 has both SMS and Email channels
        assertEquals(category1, capturedNotifications.get(2).getCategory());
        assertEquals(channel1, capturedNotifications.get(2).getChannel());
        assertEquals(users.get(0), capturedNotifications.get(2).getUser());


        // Expected notifications are persisted
        assertEquals(1L, capturedNotifications.get(3).getUser().getId());
        assertEquals("John", capturedNotifications.get(3).getUser().getFirstName());
        assertTrue(users.get(0).getChannels().contains(capturedNotifications.get(3).getChannel()));
        assertEquals(category1, capturedNotifications.get(3).getCategory());
        assertEquals("Hello", capturedNotifications.get(3).getMessage());

        assertEquals(1L, capturedNotifications.get(4).getUser().getId());
        assertEquals("John", capturedNotifications.get(4).getUser().getFirstName());
        assertTrue(users.get(0).getChannels().contains(capturedNotifications.get(4).getChannel()));
        assertEquals(category1, capturedNotifications.get(4).getCategory());
        assertEquals("Hello", capturedNotifications.get(4).getMessage());

        assertEquals(2L, capturedNotifications.get(5).getUser().getId());
        assertEquals("Sandra", capturedNotifications.get(5).getUser().getFirstName());
        assertEquals(channel2, capturedNotifications.get(5).getChannel());
        assertEquals(category1, capturedNotifications.get(5).getCategory());
        assertEquals("Hello", capturedNotifications.get(5).getMessage());
    }

    @Configuration
    static class TestConfig {

        @Bean
        public AMQPPublisher amqpPublisher() {
            return Mockito.mock(AMQPPublisher.class);
        }

        @Bean
        public SMSNotificationSender smsNotificationSender(AMQPPublisher amqpPublisher) {
            return new SMSNotificationSender(amqpPublisher);
        }

        @Bean
        public EmailNotificationSender emailNotificationSender(AMQPPublisher amqpPublisher) {
            return new EmailNotificationSender(amqpPublisher);
        }

        @Bean
        public PushNotificationSender pushNotificationSender() {
            return Mockito.mock(PushNotificationSender.class);
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

        @Bean
        public NotificationSenderFactory notificationSenderFactory(
                Map<ChannelType, NotificationSender> notificationSenders
        ) {
            return new NotificationSenderFactory(notificationSenders);
        }

        @Bean
        public CategoryRepository categoryRepository() {
            return Mockito.mock(CategoryRepository.class);
        }

        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }

        @Bean
        public NotificationRepository notificationRepository() {
            return Mockito.mock(NotificationRepository.class);
        }

        @Bean
        public NotificationService notificationService(
                NotificationSenderFactory notificationSenderFactory,
                CategoryRepository categoryRepository,
                NotificationRepository notificationRepository,
                UserRepository userRepository
        ) {
            return new NotificationService(
                    notificationSenderFactory,
                    categoryRepository,
                    notificationRepository,
                    userRepository
            );
        }
    }
}
