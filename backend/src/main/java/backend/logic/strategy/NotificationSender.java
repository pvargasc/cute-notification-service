package backend.logic.strategy;

import backend.domain.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification);
}
