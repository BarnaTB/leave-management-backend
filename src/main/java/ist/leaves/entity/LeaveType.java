package ist.leaves.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Annual, Sick, etc.

    private String description;

    private double accrualRate; // Days per month

    private int maxCarryForward; // Max days to carry forward

    private boolean requiresDocument; // Whether a supporting document is required

    private boolean active; // Indicates if the leave type is active
}