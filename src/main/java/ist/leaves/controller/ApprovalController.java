package ist.leaves.controller;

import ist.leaves.service.ApprovalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approval")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/{applicationId}/approve")
    public String approveLeave(@PathVariable Long applicationId, @RequestParam String approverComments) {
        approvalService.approveLeave(applicationId, approverComments);
        return "Leave application approved successfully.";
    }

    @PostMapping("/{applicationId}/reject")
    public String rejectLeave(@PathVariable Long applicationId, @RequestParam String approverComments) {
        approvalService.rejectLeave(applicationId, approverComments);
        return "Leave application rejected successfully.";
    }
}