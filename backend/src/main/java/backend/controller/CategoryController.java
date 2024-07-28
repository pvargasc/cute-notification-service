package backend.controller;

import backend.dto.CategoryDTO;
import backend.repository.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll()
                                 .stream()
                                 .map(category -> new CategoryDTO(
                                         category.getId(),
                                         category.getType().toString(),
                                         category.getStatus().toString()
                                 ))
                                 .collect(Collectors.toList());
    }
}
