package ist.leaves.repository;

import ist.leaves.entity.LeaveApplication;
import ist.leaves.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByEmployeeId(Long employeeId);
    List<LeaveApplication> findByStatus(String status);
    List<LeaveApplication> findByLeaveType(LeaveType leaveType);
}