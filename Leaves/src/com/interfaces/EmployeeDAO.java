package com.interfaces;

import com.models.Employee;

public interface EmployeeDAO {
	Employee getEmployeeById(int emplId);
}