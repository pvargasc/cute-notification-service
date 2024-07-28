package backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Objects;


public class NotificationDTO {
    private Long id;
    private String category;
    private String channel;
    private String userFullName;
    private ZonedDateTime sentAt;
    private String message;

    public NotificationDTO() {

    }

    public NotificationDTO(Long id, String category, String channel, String userFullName, ZonedDateTime sentAt, String message) {
        this.id = id;
        this.category = category;
        this.channel = channel;
        this.userFullName = userFullName;
        this.sentAt = sentAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public ZonedDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(ZonedDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationDTO that = (NotificationDTO) o;

        if (!id.equals(that.id)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", channel='" + channel + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", sentAt=" + sentAt +
                ", message='" + message + '\'' +
                '}';
    }
}
