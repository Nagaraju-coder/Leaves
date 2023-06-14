package com.implementations;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.interfaces.EmployeeLeaveRequestDAO;
import com.models.EmployeeLeaveRequest;

@Repository
public class EmployeeLeaveRequestDAOImpl implements EmployeeLeaveRequestDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveEmployeeLeaveRequest(EmployeeLeaveRequest leaveRequest) {
		entityManager.persist(leaveRequest);
	}

	public int getNextLeaveRequestIndex(int employeeId) {
		String queryString = "SELECT COALESCE(MAX(lr.leaveRequestId.leaveRequestIndex), 0) + 1 "
				+ "FROM EmployeeLeaveRequest lr " + "WHERE lr.leaveRequestId.employeeId = :employeeId";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("employeeId", employeeId);
		return (Integer) query.getSingleResult();
	}

	// no of approved leaves in current month
	public int getApprovedLeavesCount(int employeeId) {
		LocalDate currentDate = LocalDate.now();
		LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
		LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

		String queryString = "SELECT COUNT(lr) " + "FROM EmployeeLeaveRequest lr "
				+ "WHERE lr.leaveRequestId.employeeId = :employeeId "
				+ "AND lr.approvedLeaveStartDate BETWEEN :firstDayOfMonth AND :lastDayOfMonth "
				+ "AND lr.approvedLeaveEndDate BETWEEN :firstDayOfMonth AND :lastDayOfMonth";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("employeeId", employeeId);
		query.setParameter("firstDayOfMonth", firstDayOfMonth);
		query.setParameter("lastDayOfMonth", lastDayOfMonth);

		return ((Number) query.getSingleResult()).intValue();
	}
}
