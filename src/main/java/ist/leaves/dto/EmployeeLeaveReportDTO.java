package ist.leaves.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeLeaveReportDTO {
    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}