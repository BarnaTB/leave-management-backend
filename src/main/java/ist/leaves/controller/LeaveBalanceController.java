package ist.leaves.controller;

import ist.leaves.service.LeaveBalanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave-balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @PostMapping("/process-carryover")
    public void processYearEndCarryover() {
        leaveBalanceService.processYearEndCarryover();
    }

    // Endpoint to adjust leave balance
    @PostMapping("/{id}/adjust")
    public void adjustLeaveBalance(@PathVariable Long id, @RequestParam double adjustmentAmount) {
        leaveBalanceService.adjustLeaveBalance(id, adjustmentAmount);
    }
}