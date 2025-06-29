<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="kms.model.teacher" %>
<%
  Object user = session.getAttribute("user");
  String role = (String) session.getAttribute("role");
  String adminName = "";

  if ("admin".equals(role) && user instanceof teacher) {
      teacher a = (teacher) user;
      adminName = a.getTeacherName();
  }
%>

<!DOCTYPE html>
  <html lang="en">
 <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="adminDashboard.css">
  </head>
  
<body>

  <header>
  	<button class="navSidebar" onclick="toggleSidebar()"><i class="fa-solid fa-bars"></i></button>
  	
    <div class="logo">
      <img src="image/LOGO-AL-KAUTHAR-EDUQIDS.png" alt="ALKAUTHAR EDUQIDS">
    </div>
  </header>

  <div class="sidebar" id="sidebar">
  <div class="profile">
      <a href="viewAccount.jsp"><img src="images/admin.jpg" alt="Admin Profile Photo"></a>
      <h3><%= adminName %></h3>
      <p>Administrator</p>
    </div>
    <a href="#">Dashboard</a>
    <a href="ListStudentController">Students</a>
    <a href="#">Teachers</a>
    <a href="#">Accounts</a>
    <a href="#">Logout</a>
  </div>
  
  <div class="banner">
  
  </div>

  <!-- Dashboard Content -->
  <main class="dashboard" id="dashboard">
    <div class="welcome">
      <div>
        <h2>Welcome back, Nurul Najah</h2>
        <p>Every child entrusted to your care is a trust from Allah. Through your kindness and wisdom, you are shaping the future of the ummah with love and barakah</p>
      </div>
      <div>
        <img src="image/design1.png" >
      </div>
    </div>

    <div class="cards">
      <div class="card" style="border-color: #f491ba;">
        <h3>40</h3>
        <p>Students</p>
      </div>
      <div class="card" style="border-color: #a9d96c;">
        <h3>4</h3>
        <p>Teachers</p>
      </div>
      <div class="card" style="border-color: #f491ba;">
        <h3>12</h3>
        <p>Registration</p>
      </div>
      <div class="card" style="border-color: #a9d96c;">
        <h3>3</h3>
        <p>Accounts</p>
      </div>
    </div>

    <div class="recent-activity">
      <h2>Recent Activity</h2>
      <div class="activity-box">
       <ul class="activity-list">
        <li>
        <div class="activity-item">
        <img src="images/parent.jpg" alt="Parents Profile">
        <span><a href=" ">@username</a> has submitted a request for account deletion</span>
        </div>
        <div class="btn-group">
          <button class="delete-btn">Delete</button>
          <button class="cancel-btn">Cancel</button>
        </div>
        </ul>
      </div>
    </div>
  </main>

  <!-- Script -->
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