package com.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.interfaces.EmployeeDAO;
import com.models.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Employee getEmployeeById(int emplId) {
		return entityManager.find(Employee.class, emplId);
	}
}