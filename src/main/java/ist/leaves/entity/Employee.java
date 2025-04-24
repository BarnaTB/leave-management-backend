package ist.leaves.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String microsoftId;

    @Column(unique = true)
    private String email;

    private String name;
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    private double leaveBalance; // Leave balance for the employee

    private boolean active;

    @OneToMany(mappedBy = "employee")
    private List<LeaveApplication> leaveApplications;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }
}
