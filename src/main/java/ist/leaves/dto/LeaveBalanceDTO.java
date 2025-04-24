package ist.leaves.dto;

import lombok.Data;

@Data
public class LeaveBalanceDTO {
    private String leaveType;
    private int balance;

    public LeaveBalanceDTO(String leaveType, int balance) {
        this.leaveType = leaveType;
        this.balance = balance;
    }
}