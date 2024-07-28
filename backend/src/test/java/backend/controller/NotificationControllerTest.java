package backend.controller;

import backend.dto.NotificationDTO;
import backend.dto.NotificationRequest;
import backend.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;
    @Test
    public void testFindAllNotifications() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO(1L, "Type1", "Channel1", "User1", null, "Message1");

        when(notificationService.findAllNotifications()).thenReturn(List.of(notificationDTO));

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'category':'Type1','channel':'Channel1','userFullName':'User1','message':'Message1'}]"));

        verify(notificationService).findAllNotifications();
    }

    @Test
    public void testSendNotification() throws Exception {
        NotificationRequest notificationRequest = new NotificationRequest("Type1", "Message1");

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"Type1\",\"message\":\"Message1\"}"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Notification(s) sent."));

        verify(notificationService).handleNotificationRequest(any(NotificationRequest.class));
    }
}
