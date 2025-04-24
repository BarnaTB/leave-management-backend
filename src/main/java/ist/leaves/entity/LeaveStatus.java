package ist.leaves.entity;

public enum LeaveStatus {
    PENDING("Pending Review"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PENDING_MANAGER_APPROVAL("Pending Manager Approval"),
    PENDING_ADMIN_APPROVAL("Pending Admin Approval");

    private final String displayName;

    LeaveStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}