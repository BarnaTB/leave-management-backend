package ist.leaves.dto;

import lombok.Data;

import java.util.List;

@Data
public class LeaveTypeReportDTO {
    private String leaveType;
    private List<EmployeeLeaveReportDTO> employeeLeaveReports;
}