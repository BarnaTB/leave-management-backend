package ist.leaves.dto;

import lombok.Data;

@Data
public class LeaveBalanceAdjustmentRequest {
    private Long employeeId;
    private double adjustmentAmount;
    private String reason;
}