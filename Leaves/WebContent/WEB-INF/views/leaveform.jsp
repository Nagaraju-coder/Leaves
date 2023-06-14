<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Leave Form</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

h1 {
	font-size: 24px;
	margin-bottom: 10px;
}

.form-group {
	margin-bottom: 20px;
}

.form-group label {
	font-weight: bold;
	display: block;
}

.form-group input[type="text"], .form-group input[type="date"],
	.form-group select {
	width: 300px;
	padding: 5px;
}

.form-group textarea {
	width: 300px;
	height: 100px;
	padding: 5px;
}

.submit-button {
	margin-top: 10px;
	font-size: 16px;
	padding: 10px 20px;
}
</style>

<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
</head>
<body>
	<h1>Leave Form</h1>

	<form id="leave-form" method="post">

		<div class="form-group">
			<label for="employee-id">Employee ID:</label> <input type="number"
				id="employee-id" name="employeeId"
				value="${sessionScope.employeeid}" readonly>
		</div>


		<div class="form-group">
			<label for="leave-start-date">Leave Start Date:</label> <input
				type="date" id="leave-start-date" name="leaveStartDate" required>
		</div>

		<div class="form-group">
			<label for="leave-end-date">Leave End Date:</label> <input
				type="date" id="leave-end-date" name="leaveEndDate" required>
		</div>

		<div class="form-group">
			<label for="leave-type">Leave Type:</label> <select id="leave-type"
				name="leaveType">
				<option value="CASL">CASL</option>
				<option value="SICK">SICK</option>
				<option value="OTHR">OTHR</option>
			</select>
		</div>

		<div class="form-group">
			<label for="reason">Reason:</label>
			<textarea id="reason" name="reason" required></textarea>
		</div>
		
		

		<input class="submit-button" type="button" value="Submit" onclick="submitLeave()">
	</form>
	<script>
	function submitLeave() {
		 
     console.log($("#leave-form").serialize())
		

		  // Send the Ajax request
		  $.ajax({
		    url: "submitleave",
		    type: "POST",
		    data: $("#leave-form").serialize(),
		    success: function(response) {
		      console.log("Leave request submitted successfully");
		      // Perform any additional actions or display a success message
		    },
		    error: function(xhr, status, error) {
		      console.log("Error submitting leave request:", error);
		      // Display an error message or handle the error as needed
		    }
		  });
		}
	</script>
</body>
</html>
