package backend.controller;

import backend.dto.NotificationDTO;
import backend.dto.NotificationRequest;
import backend.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    @Cacheable("caffeine-cache")
    public List<NotificationDTO> findAllNotifications() {
        return notificationService.findAllNotifications();
    }

    @PostMapping()
    @CacheEvict(value = "caffeine-cache", allEntries = true)
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationRequest notificationRequest) {
        notificationService.handleNotificationRequest(notificationRequest);
        return ResponseEntity.ok("Notification(s) sent.");
    }
}
