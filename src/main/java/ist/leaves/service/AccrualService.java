package ist.leaves.service;

import ist.leaves.entity.Employee;
import ist.leaves.entity.LeaveBalance;
import ist.leaves.entity.LeaveType;
import ist.leaves.repository.EmployeeRepository;
import ist.leaves.repository.LeaveBalanceRepository;
import ist.leaves.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccrualService {

    private final LeaveBalanceRepository balanceRepository;
    private final LeaveTypeRepository typeRepository;
    private final EmployeeRepository employeeRepository;

    @Scheduled(cron = "0 0 0 1 * ?") // Monthly accrual
    public void processMonthlyAccrual() {
        List<LeaveType> activeTypes = typeRepository.findByActiveTrue();
        List<Employee> activeEmployees = employeeRepository.findByActiveTrue();

        activeEmployees.forEach(employee ->
                activeTypes.forEach(type -> {
                    LeaveBalance balance = balanceRepository
                            .findByEmployeeAndLeaveType(employee, type)
                            .orElseGet(() -> createNewBalance(employee, type));

                    double accrual = type.getAccrualRate();
                    balance.setCurrentBalance(balance.getCurrentBalance() + accrual);
                    balanceRepository.save(balance);
                })
        );
    }

    @Scheduled(cron = "0 0 0 1 1 ?") // Yearly carry-forward
    public void processYearEndCarryover() {
        balanceRepository.findAll().forEach(balance -> {
            int maxCarry = balance.getLeaveType().getMaxCarryForward();
            double carried = Math.min(balance.getCurrentBalance(), maxCarry);

            balance.setCarriedOver(carried);
            balance.setCurrentBalance(carried);
            balanceRepository.save(balance);
        });
    }

    private LeaveBalance createNewBalance(Employee employee, LeaveType type) {
        LeaveBalance newBalance = new LeaveBalance();
        newBalance.setEmployee(employee);
        newBalance.setLeaveType(type);
        newBalance.setCurrentBalance(0.0);
        newBalance.setCarriedOver(0.0);
        return newBalance;
    }
}
