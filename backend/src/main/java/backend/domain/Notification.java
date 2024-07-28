package backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sent_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime sentAt;

    @Column(nullable = false)
    private String message;

    public Notification() {

    }

    public Notification(Long id, Category category, Channel channel, User user, ZonedDateTime sentAt, String message) {
        this.id = id;
        this.category = category;
        this.channel = channel;
        this.user = user;
        this.sentAt = sentAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        Notification that = (Notification) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (!Objects.equals(channel, that.channel)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(sentAt, that.sentAt)) return false;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", category=" + category +
                ", channel=" + channel +
                ", user=" + user +
                ", sentAt=" + sentAt +
                ", message='" + message + '\'' +
                '}';
    }
}