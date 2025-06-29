<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="kms.model.*" %>
<%@ page session="true" %>
<%
    Object user = session.getAttribute("user");
    String role = (String) session.getAttribute("role");
    

    int id = 0;
    String name = "", email = "", phone = "", roleDisplay = "", teacherType = "";
    double salary = 0.0;
    int contract = 0;

    if (role != null && user != null) {
        if (role.equals("parent") && user instanceof parent) {
            parent p = (parent) user;
            id = p.getParentId();
            name = p.getParentName();
            email = p.getParentEmail();
            phone = p.getParentPhone();
            roleDisplay = "Parent";
        } else if ((role.equalsIgnoreCase("teacher") || role.equalsIgnoreCase("admin")) && user instanceof teacher) {
            teacher t = (teacher) user;
            id = t.getTeacherId();
            name = t.getTeacherName();
            email = t.getTeacherEmail();
            phone = t.getTeacherPhone();
            roleDisplay = role.equals("admin") ? "Administrator" : "Teacher";
            teacherType = t.getTeacherType();

            if (t instanceof fullTime) {
                salary = ((fullTime) t).getSalary();
            } else if (t instanceof partTime) {
                contract = ((partTime) t).getContract();
            }
        }
    }
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Account</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<link rel="stylesheet" href="css/viewAccount.css">
</head>
	
<body>

	<header>
		<button class="navSidebar" onclick="toggleSidebar()">
			<i class="fa-solid fa-bars"></i>
		</button>
		<div class="logo">
			<img src="images/LOGO-AL-KAUTHAR-EDUQIDS.png"
				alt="ALKAUTHAR EDUQIDS Logo">
		</div>
	</header>

	<nav class="sidebar" id="sidebar">
		<div class="profile">
			<a href="viewAccount.jsp">  <img src="PhotoServlet?role=<%= role %>&type=photo&id=<%= id %>" ></a>
			<h3><%=name%></h3>

			<p><%= roleDisplay %></p>
		</div>
		<a href="#">Dashboard</a> 
		<a href="ListStudentController">Student Registration</a> 
		<a href="#">Teachers</a> 
		<a href="#">Accounts</a> 
		<a href="#">Logout</a>
	</nav>

	<main class="account-container">
		<div class="account-card">
			<h1>My Account</h1>
			<a href="updateAccount.jsp">
  <button class="edit-btn">Edit Account</button>
</a>

			<div class="profile-info">
				<div class="profile-img">
 <img src="PhotoServlet?role=<%= role %>&type=photo&id=<%= id %>" style="width: 200px; height: 200px; object-fit: cover; border-radius: 10px;" /></div>
				<div class="details">
					<div class="column">
						<p>
							<strong>Name</strong><br><%=name %>
						</p>
						
						<p>
							<strong>E-mail</strong><br><%=email %>
						</p>
						<p>
							<strong>Phone Number</strong><br><%=phone %>
						</p>
						
						<p>
							<strong>Role</strong><br><%=role %>
						</p>
					</div>
					<div class="column">
						
						<% if ("FullTime".equalsIgnoreCase(teacherType)) { %>
            <p><strong>Salary</strong><br>RM <%= salary %></p>
          <% } else if ("PartTime".equalsIgnoreCase(teacherType)) { %>
            <p><strong>Contract (Hours):</strong><br><%= contract %></p>
          <% } %>
					</div>
				</div>
			</div>
		</div>
	</main>
	 <script>
    function toggleSidebar() {
      const sidebar = document.getElementById("sidebar");
      const dashboard = document.getElementById("dashboard");
      sidebar.classList.toggle("show");
      dashboard.classList.toggle("shifted");
    }
  </script>
</body>
</html>