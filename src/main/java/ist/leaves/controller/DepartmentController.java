package ist.leaves.controller;

import ist.leaves.dto.AssignEmployeeToDepartmentDTO;
import ist.leaves.dto.DepartmentDTO;
import ist.leaves.entity.Employee;
import ist.leaves.service.AdminService;
import ist.leaves.service.DepartmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final AdminService adminService;

    public DepartmentController(DepartmentService departmentService, AdminService adminService) {
        this.departmentService = departmentService;
        this.adminService = adminService;
    }

    @PostMapping
    public void createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        departmentService.createDepartment(departmentDTO);
    }

    @PostMapping("/assign-employee")
    public void assignEmployeeToDepartment(@RequestBody AssignEmployeeToDepartmentDTO dto) {
        departmentService.assignEmployeeToDepartment(dto);
    }

    @GetMapping("/{departmentId}/employees-on-leave")
    public List<Employee> getEmployeesOnLeaveByDepartment(@PathVariable Long departmentId) {
        return departmentService.getEmployeesOnLeaveByDepartment(departmentId);
    }

    @PatchMapping("/employees/{employeeId}/role")
    public void updateEmployeeRole(@PathVariable Long employeeId, @RequestParam String newRole) {
        adminService.updateEmployeeRole(employeeId, newRole);
    }
}