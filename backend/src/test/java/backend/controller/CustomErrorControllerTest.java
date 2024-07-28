package backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CustomErrorController.class)
public class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testError404() throws Exception {
        mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
                                                .andExpect(status().isOk())
                                                .andExpect(view().name("error-404"));
    }

    @Test
    public void testError500() throws Exception {
        mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
                                                .andExpect(status().isOk())
                                                .andExpect(view().name("error-500"));
    }

    @Test
    public void testDefaultError() throws Exception {
        mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400))
                                                .andExpect(status().isOk())
                                                .andExpect(view().name("error"));
    }
}
