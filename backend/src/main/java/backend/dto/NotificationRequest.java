package backend.dto;

import backend.domain.Category;
import backend.domain.Channel;
import backend.shared.CategoryType;
import backend.shared.ChannelType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotEmpty(message = "Message cannot be empty")
    @Size(max = 255, message = "Message cannot exceed 255 characters")
    private String message;
}
