package ist.leaves.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class LeaveApplicationRequest {
    private Long employeeId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean halfDay;
    private String reason;
    private MultipartFile document; // Uploaded document
}