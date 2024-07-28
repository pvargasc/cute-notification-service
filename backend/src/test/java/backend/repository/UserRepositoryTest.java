package backend.repository;

import backend.domain.Category;
import backend.domain.Channel;
import backend.domain.User;
import backend.shared.CategoryType;
import backend.shared.Status;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        Optional<User> userOptional = userRepository.findById(1L);
        assertTrue(userOptional.isPresent());

        User user = userOptional.get();

        assertEquals("Alice", user.getFirstName());
        assertEquals("Johnson", user.getLastName());
        assertEquals("5551234567", user.getPhoneNumber());
        assertEquals("alice.johnson@example.com", user.getEmail());
    }

    @Test
    public void testFindAll() {
        List<User> channels = userRepository.findAll();

        assertFalse(channels.isEmpty());
        assertEquals(12, channels.size());
    }

    @Test
    public void testFindByCategory() {
        Category category = new Category(1L, CategoryType.MOVIES, Status.ACTIVE);

        List<User> users = userRepository.findByCategory(category);

        assertEquals(10, users.size());

        boolean allMatch = users.stream()
                .allMatch(user -> user.getCategories()
                        .stream()
                        .anyMatch(inner -> "MOVIES".equals(category.getType().toString())));

        assertTrue(allMatch);
    }
}
