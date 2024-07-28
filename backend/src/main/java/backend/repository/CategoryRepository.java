package backend.repository;

import backend.domain.Category;
import backend.shared.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByType(CategoryType type);
}
