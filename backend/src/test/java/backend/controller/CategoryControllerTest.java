package backend.controller;

import backend.controller.CategoryController;
import backend.domain.Category;
import backend.dto.CategoryDTO;
import backend.repository.CategoryRepository;
import backend.shared.CategoryType;
import backend.shared.Status;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllCategories() throws Exception {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(
                        new Category(1L, CategoryType.MOVIES, Status.ACTIVE),
                        new Category(2L, CategoryType.FINANCE, Status.ACTIVE),
                        new Category(3L, CategoryType.SPORTS, Status.ACTIVE)
                )
        );

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{'id':1,'type':'MOVIES','status':'ACTIVE'}," +
                        "{'id':2,'type':'FINANCE','status':'ACTIVE'}," +
                        "{'id':3,'type':'SPORTS','status':'ACTIVE'}]"
                ));

        verify(categoryRepository).findAll();
    }
}