package ist.leaves.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean halfDay;
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status; // Enum for leave status

    private String approverComments; // Comments from approvers
    private String documentPath; // Path to uploaded document
}