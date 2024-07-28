package backend.repository;

import backend.domain.Category;
import backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.categories c WHERE c = :category")
    List<User> findByCategory(@Param("category") Category category);
}
