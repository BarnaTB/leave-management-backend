package ist.leaves.controller;

import ist.leaves.dto.LeaveApplicationRequest;
import ist.leaves.dto.LeaveBalanceDTO;
import ist.leaves.entity.LeaveApplication;
import ist.leaves.service.LeaveApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@PreAuthorize("hasRole('STAFF')")
public class EmployeeController {

    private final LeaveApplicationService leaveApplicationService;

    public EmployeeController(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    @GetMapping("/leave-balance")
    public List<LeaveBalanceDTO> getLeaveBalance(@RequestParam Long employeeId) {
        return leaveApplicationService.getLeaveBalances(employeeId);
    }

    @PostMapping("/apply-leave")
    public LeaveApplication applyForLeave(@RequestBody LeaveApplicationRequest request) {
        try {
            return leaveApplicationService.applyForLeave(request);
        } catch (IOException e) {
            throw new RuntimeException("Error while applying for leave", e);
        }
    }

    @GetMapping("/leave-history")
    public List<LeaveApplication> getLeaveHistory(@RequestParam Long employeeId) {
        return leaveApplicationService.getLeaveHistory(employeeId);
    }
}