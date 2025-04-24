package ist.leaves.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class LeaveType {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
}
