package backend.repository;

import backend.domain.Category;
import backend.domain.Channel;
import backend.shared.CategoryType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindById() {
        Optional<Category> category = categoryRepository.findById(1L);

        assertTrue(category.isPresent());
        assertEquals("SPORTS",category.get().getType().toString());
    }

    @Test
    public void testFindAll() {
        List<Category> categories = categoryRepository.findAll();

        assertFalse(categories.isEmpty());
        assertEquals(3, categories.size());
        assertEquals("SPORTS", categories.get(0).getType().toString());
        assertEquals("FINANCE", categories.get(1).getType().toString());
        assertEquals("MOVIES", categories.get(2).getType().toString());
    }

    @Test
    public void testFindByType() {
        CategoryType type = CategoryType.SPORTS;
        Category category = categoryRepository.findByType(type);
        System.out.println(category);
    }
}
