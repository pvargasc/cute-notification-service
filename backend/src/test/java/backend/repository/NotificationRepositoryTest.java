package backend.repository;

import backend.domain.Category;
import backend.domain.Notification;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testFindById() {
        Optional<Notification> notificationOptional = notificationRepository.findById(1L);

        assertTrue(notificationOptional.isPresent());

        Notification notification = notificationOptional.get();

        assertEquals("SPORTS", notification.getCategory().getType().name());
        assertEquals("EMAIL", notification.getChannel().getType().name());
        assertEquals("Charlie", notification.getUser().getFirstName());
        assertEquals("Brown", notification.getUser().getLastName());
        assertEquals("Ophiophagus hannah", notification.getMessage());
    }

    @Test
    public void testFindAll() {
        List<Notification> categories = notificationRepository.findAll();

        assertFalse(categories.isEmpty());
        assertEquals(36, categories.size());
    }

    @Test
    public void testFindAllByOrderBySentAtDesc() {
        List<Notification> notifications = notificationRepository.findAllByOrderBySentAtDesc();

        boolean listIsInDescendingOrderByDate = IntStream.range(0, notifications.size() - 1)
                                                         .noneMatch(
                                                                 i -> notifications.get(i)
                                                                         .getSentAt()
                                                                         .isBefore(
                                                                                 notifications.get(i + 1)
                                                                                         .getSentAt()
                                                                         )
                                                         );


        assertTrue(listIsInDescendingOrderByDate);
    }
}
