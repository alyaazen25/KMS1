<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="kms.model.teacher" %>
<%
  Object user = session.getAttribute("user");
  String teacherName = "";

  if (user instanceof teacher) {
      teacher t = (teacher) user;
      teacherName = t.getTeacherName();
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Teacher Dashboard - Alkauthar Eduqids</title>
  <link rel="stylesheet" href="teacherDashboard.css" />
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
  />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
  <header>
    <button class="navSidebar" onclick="toggleSidebar()">
      <i class="fa-solid fa-bars"></i>
    </button>
    <div class="logo">
      <img src="image/logo.png" alt="ALKAUTHAR EDUQIDS Logo" />
    </div>
  </header>

  <nav class="sidebar" id="sidebar">
    <div class="profile">
      <a href="viewAccount.jsp">
        <img src="images/admin.jpg" alt="Teacher Profile Photo" />
      </a>
      <h3><%= teacherName %></h3>
      <p>Teacher</p>
    </div>
   <ul class="sidebar-links top-links">
  <li><a href="teacherDashboard.jsp"><i class="fas fa-home"></i> Home</a></li>
  <li><a href="ViewSubjectTeacherController"><i class="fas fa-book"></i> Subject</a></li>
  <li><a href="#"><i class="fas fa-calendar-check"></i> Attendance</a></li>
  <li><a href="#"><i class="fas fa-chart-line"></i> Progress</a></li>
  <li><a href="#"><i class="fas fa-user-graduate"></i> Student</a></li>
</ul>

<ul class="sidebar-links bottom-links">
  <li><a href="#"><i class="fas fa-user-cog"></i> Account</a></li>
  <li><a href="#"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
</ul>

  </nav>

  <div class="banner">
    <img
      src="image/bannerteacher.jpg"
      alt="Banner Image"
      class="banner-image"
    />
  </div>

  <div class="dashboard" id="dashboard">
    <h2>Welcome, Teacher!</h2>
    <div class="card-container">
      <div class="card">
        <i class="fa-solid fa-user-graduate"></i>
        <h3>My Students</h3>
        <p>View and manage your student list.</p>
      </div>
      <div class="card">
        <i class="fa-solid fa-calendar-check"></i>
        <h3>Attendance</h3>
        <p>Mark and review attendance records.</p>
      </div>
      <div class="card">
        <i class="fa-solid fa-chart-line"></i>
        <h3>Student Progress</h3>
        <p>Monitor students' development and milestones.</p>
      </div>
    </div>
  </div>

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
