package backend.service;

import backend.domain.Category;
import backend.domain.Channel;
import backend.domain.Notification;
import backend.domain.User;
import backend.dto.NotificationDTO;
import backend.dto.NotificationRequest;
import backend.logic.strategy.NotificationSender;
import backend.logic.factory.NotificationSenderFactory;
import backend.repository.CategoryRepository;
import backend.repository.NotificationRepository;
import backend.repository.UserRepository;
import backend.shared.CategoryType;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationSenderFactory notificationSenderFactory;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public NotificationService(NotificationSenderFactory notificationSenderFactory, CategoryRepository categoryRepository, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationSenderFactory = notificationSenderFactory;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<NotificationDTO> findAllNotifications() {
        final List<Notification> fetchedNotifications = notificationRepository.findAllByOrderBySentAtDesc();

        return fetchedNotifications.stream().map(notification -> new NotificationDTO(
                notification.getId(),
                notification.getCategory().getType().toString(),
                notification.getChannel().getType().toString(),
                notification.getUser().getFirstName() + " " + notification.getUser().getLastName(),
                notification.getSentAt(),
                notification.getMessage())
        ).collect(Collectors.toList());
    }

    public void handleNotificationRequest(NotificationRequest notificationRequest) {

        final Category category = getCategoryFromCategoryTypeAsString(notificationRequest.getCategory());
        final List<User> usersRegisteredToRequestCategory = userRepository.findByCategory(category);

        for (User user : usersRegisteredToRequestCategory) {

            for (Channel channel : user.getChannels()) {

                final Notification notification = new Notification();
                notification.setUser(user);
                notification.setChannel(channel);
                notification.setCategory(category);
                notification.setMessage(notificationRequest.getMessage());
                notification.setSentAt(ZonedDateTime.now());

                final NotificationSender notificationSender = notificationSenderFactory.getNotificationSender(channel.getType());
                notificationSender.sendNotification(notification);

                notificationRepository.save(notification);

                LOGGER.info(
                        "Notification sent: User={} | {}, Category={}, Channel={}",
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        notificationRequest.getCategory(),
                        channel.getType()
                );
            }
        }
    }

    public Category getCategoryFromCategoryTypeAsString(String categoryTypeAsString) {
        CategoryType categoryType = null;

        for (CategoryType innerCategoryType : CategoryType.values()) {
            if (innerCategoryType.name().equalsIgnoreCase(categoryTypeAsString)) {
                categoryType = innerCategoryType;
            }
        }

        if (categoryType != null) {
            return categoryRepository.findByType(categoryType);
        }

        throw new IllegalArgumentException("Category type " + categoryTypeAsString + " doesn't exist");
    }
}
