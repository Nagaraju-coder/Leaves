package com.interfaces;

import com.models.EmployeeLeaveRequest;

public interface EmployeeLeaveRequestDAO {
	void saveEmployeeLeaveRequest(EmployeeLeaveRequest leaveRequest);

	int getNextLeaveRequestIndex(int employeeId);

	int getApprovedLeavesCount(int employeeId);
}
