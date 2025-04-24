package ist.leaves.controller;

import ist.leaves.dto.LeaveApplicationRequest;
import ist.leaves.entity.LeaveApplication;
import ist.leaves.service.LeaveApplicationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/leave-applications")
public class LeaveApplicationController {

    private final LeaveApplicationService leaveApplicationService;

    public LeaveApplicationController(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    @PostMapping("/apply")
    public LeaveApplication applyForLeave(@ModelAttribute LeaveApplicationRequest request) throws IOException {
        return leaveApplicationService.applyForLeave(request);
    }

    @PostMapping("/{id}/approve/manager")
    public LeaveApplication approveByManager(@PathVariable Long id, @RequestParam String approverComments) {
        return leaveApplicationService.approveByManager(id, approverComments);
    }

    @PostMapping("/{id}/approve/admin")
    public LeaveApplication approveByAdmin(@PathVariable Long id, @RequestParam String approverComments) {
        return leaveApplicationService.approveByAdmin(id, approverComments);
    }

    @PostMapping("/{id}/reject")
    public LeaveApplication rejectLeave(@PathVariable Long id, @RequestParam String approverComments) {
        return leaveApplicationService.rejectLeave(id, approverComments);
    }
}