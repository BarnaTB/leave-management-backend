package ist.leaves.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    private double currentBalance; // Current leave balance
    private double accruedThisYear; // Leave accrued in the current year
    private double carriedOver; // Leave carried over from the previous year
}