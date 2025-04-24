package ist.leaves.service;

import ist.leaves.dto.AssignEmployeeToDepartmentDTO;
import ist.leaves.dto.DepartmentDTO;
import ist.leaves.entity.Department;
import ist.leaves.entity.Employee;
import ist.leaves.entity.LeaveStatus;
import ist.leaves.exception.ResourceNotFoundException;
import ist.leaves.repository.DepartmentRepository;
import ist.leaves.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public void createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }

    public void assignEmployeeToDepartment(AssignEmployeeToDepartmentDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesOnLeaveByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return employeeRepository.findByDepartment(department).stream()
                .filter(employee -> employee.getLeaveApplications().stream()
                        .anyMatch(leave -> leave.getStatus() == LeaveStatus.APPROVED))
                .toList();
    }
}