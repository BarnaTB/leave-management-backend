package ist.leaves.service;

import ist.leaves.entity.Employee;
import ist.leaves.entity.LeaveBalance;
import ist.leaves.entity.LeaveType;
import ist.leaves.exception.ResourceNotFoundException;
import ist.leaves.repository.EmployeeRepository;
import ist.leaves.repository.LeaveBalanceRepository;
import ist.leaves.repository.LeaveTypeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository,
                               EmployeeRepository employeeRepository,
                               LeaveTypeRepository leaveTypeRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Daily job to update leave balance of all employees based on their leave type accrual rate.
     * <p>
     * This job runs every day at midnight and accrues leave balance of all employees for all leave types.
     * <p>
     * The accrual rate is divided by 30 days to get the daily accrual rate.
     * <p>
     * For example, if the accrual rate is 10 days per month, the daily accrual rate will be 10/30 = 0.3333 days.
     * <p>
     * The daily accrual rate is then added to the current balance of the employee for that leave type.
     * <p>
     * The updated balance is then saved in the database.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    public void updateDailyLeaveBalance() {
        List<Employee> employees = employeeRepository.findAll();
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        employees.forEach(employee ->
                leaveTypes.forEach(leaveType -> {
                    LeaveBalance balance = leaveBalanceRepository
                            .findByEmployeeAndLeaveType(employee, leaveType)
                            .orElseGet(() -> createNewBalance(employee, leaveType));

                    double dailyAccrual = leaveType.getAccrualRate() / 30; // Assuming monthly accrual divided by 30 days
                    balance.setCurrentBalance(balance.getCurrentBalance() + dailyAccrual);
                    leaveBalanceRepository.save(balance);
                })
        );
    }

    /**
     * Creates a new leave balance for the given employee and leave type.
     * <p>
     * The created balance will have a current balance, accrued this year, and carried over set to 0.
     * <p>
     * @param employee the employee to create the balance for
     * @param leaveType the leave type to create the balance for
     * @return the created leave balance
     */
    private LeaveBalance createNewBalance(Employee employee, LeaveType leaveType) {
        LeaveBalance balance = new LeaveBalance();
        balance.setEmployee(employee);
        balance.setLeaveType(leaveType);
        balance.setCurrentBalance(0);
        balance.setAccruedThisYear(0);
        balance.setCarriedOver(0);
        return balance;
    }

    /**
     * Process year-end carry over for all leave balances.
     * <p>
     * This method will loop through all leave balances and for each balance, it will calculate the
     * amount to be carried over to the next year based on the leave type's maximum carry over amount.
     * The current balance will then be reset to the carried over amount.
     * <p>
     * This method is transactional, meaning that it will be rolled back if an exception is thrown.
     */
    @Transactional
    public void processYearEndCarryover() {
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();

        leaveBalances.forEach(balance -> {
            LeaveType leaveType = balance.getLeaveType();
            int maxCarryOver = leaveType.getMaxCarryForward(); // Assume this field exists in LeaveType
            double currentBalance = balance.getCurrentBalance();

            double carryOver = Math.min(currentBalance, maxCarryOver);
            balance.setCarriedOver(carryOver);
            balance.setCurrentBalance(carryOver); // Reset current balance to carried over amount
            leaveBalanceRepository.save(balance);
        });
    }

    /**
     * Adjusts the current balance of a leave balance by the given amount.
     * <p>
     * This method is transactional, meaning that it will be rolled back if an exception is thrown.
     * <p>
     * @param leaveBalanceId the ID of the leave balance to be adjusted
     * @param adjustmentAmount the amount by which to adjust the current balance
     * @throws ResourceNotFoundException if the leave balance with the given ID could not be found
     */
    @Transactional
    public void adjustLeaveBalance(Long leaveBalanceId, double adjustmentAmount) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(leaveBalanceId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveBalance not found with id: " + leaveBalanceId));

        leaveBalance.setCurrentBalance(leaveBalance.getCurrentBalance() + adjustmentAmount);
        leaveBalanceRepository.save(leaveBalance);
    }
}