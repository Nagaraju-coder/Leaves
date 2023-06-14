package com.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.interfaces.EmployeeLeaveRequestDAO;
import com.models.EmployeeLeaveRequest;
import com.models.EmployeeLeaveRequestId;

@Controller
public class LeaveController {

	private final EmployeeLeaveRequestDAO leaveRequestDAO;

	@Autowired
	public LeaveController(EmployeeLeaveRequestDAO leaveRequestDAO) {
		this.leaveRequestDAO = leaveRequestDAO;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {

		System.out.println("in");

		HttpSession ses = request.getSession();
		ses.setAttribute("login", true);
		ses.setAttribute("employeeid", "2");
		return "login";
	}

	@RequestMapping(value = "/leaveform", method = RequestMethod.GET)
	public String leaverequest(HttpServletRequest request) {

		HttpSession ses = request.getSession(false);
		System.out.println(ses.getAttribute("login"));
		if (ses.getAttribute("login") == null || !(boolean) ses.getAttribute("login"))
			return "login";
		else
			return "leaveform";
	}

	@Transactional
	@RequestMapping(value = "/submitleave", method = RequestMethod.POST)
	public String submitLeaveRequest(@RequestParam("employeeId") int employeeId,
			@RequestParam("leaveStartDate") String leaveStartDate, @RequestParam("leaveEndDate") String leaveEndDate,
			@RequestParam("leaveType") String leaveType, @RequestParam("reason") String reason, HttpSession session) {

		EmployeeLeaveRequest leaveRequest = new EmployeeLeaveRequest();
		leaveRequest.setLeaveStartDate(LocalDate.parse(leaveStartDate, DateTimeFormatter.ISO_DATE));
		leaveRequest.setLeaveEndDate(LocalDate.parse(leaveEndDate, DateTimeFormatter.ISO_DATE));
		leaveRequest.setLeaveType(leaveType);
		leaveRequest.setReason(reason);
		leaveRequest.setRequestDateTime(LocalDateTime.now());

		EmployeeLeaveRequestId leaveRequestId = leaveRequest.getLeaveRequestId();
		if (leaveRequestId == null) {
			leaveRequestId = new EmployeeLeaveRequestId();
			leaveRequestId.setEmployeeId(employeeId);
			leaveRequest.setLeaveRequestId(leaveRequestId);
		} else if (leaveRequestId.getEmployeeId() == 0) {
			leaveRequestId.setEmployeeId(employeeId);
		}

		int nextLeaveRequestIndex = leaveRequestDAO.getNextLeaveRequestIndex(employeeId);
		leaveRequestId.setLeaveRequestIndex(nextLeaveRequestIndex);

		leaveRequestDAO.saveEmployeeLeaveRequest(leaveRequest);

		return "success";
	}

	@RequestMapping(value = "/leaveStatistics", method = RequestMethod.GET)
	public void statistics(HttpSession session) {

		System.out.println("approved" + leaveRequestDAO
				.getApprovedLeavesCount(Integer.parseInt(session.getAttribute("employeeid").toString().trim())));

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession(false).invalidate();
		return "login";
	}
}
