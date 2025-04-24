package ist.leaves.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentLeaveReportDTO {
    private String departmentName;
    private List<EmployeeLeaveReportDTO> employeeLeaveReports;
}