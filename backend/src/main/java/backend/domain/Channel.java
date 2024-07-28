package backend.domain;

import java.io.Serializable;
import java.util.Set;

import backend.shared.Status;
import backend.shared.ChannelType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channels")
public class Channel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChannelType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_code")
    private Status status;

    @ManyToMany(mappedBy = "channels")
    private Set<User> users;

    public Channel() {

    }

    public Channel(Long id, ChannelType type, Status status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        return id.equals(channel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
