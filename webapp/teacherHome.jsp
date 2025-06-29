<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Teacher Homepage - Al Kauthar Eduqids</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/teacherHome.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
    
</head>

<body>

<header>
  <button class="navSidebar" onclick="toggleSidebar()">
    <i class="fa-solid fa-bars"></i>
  </button>
  <div class="navbar">
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
  <div class="_separator_1e1cy_1"></div>
  <ul class="sidebar-links bottom-links">
    <li><a href="#"><i class="fas fa-user-cog"></i> Account</a></li>
    <li><a href="#"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
  </ul>
</nav>

<!-- WRAP your main container in dashboard div -->
<div id="dashboard" class="dashboard">
  <main>
    <div class="container">
      <section class="welcome-box">
        <div class="welcome-text">
          <h2>Hi, <%= teacherName %></h2>
          <p>Every child entrusted to your care is a trust from Allah. <br/>
             Through your kindness and wisdom, you are shaping the future of the ummah with love and barakah.</p>
        </div>
        <div class="welcome-image">
          <img src="images/avatar1.png" alt="Teacher"/>
        </div>
      </section>

      <section class="stats">
          <div class="stat green">
              <i class="fas fa-user-graduate"></i>
              <h3>40</h3>
              <p>Students</p>
          </div>
          <div class="stat pink">
              <i class="fas fa-chalkboard-teacher"></i>
              <h3>${subjectName}</h3>
              <p>Assigned</p>
          </div>
      </section>

      <hr/>

      <section class="main-section">
        <div class="left-section">
          <div class="quotes">
            <h3>Quotes</h3>
            <div class="quote-box">${randomQuote}</div>
          </div>

          <div class="tips">
            <h3>Teaching Tips</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
          </div>

          <div class="buttons">
            <div class="btn-row">
              <a href="recordAttendance.jsp" class="btn"><i class="fas fa-calendar-check"></i><p>Record Attendance</p></a>
              <a href="recordProgress.jsp" class="btn"><i class="fas fa-chart-line"></i><p>Record Progress</p></a>
            </div>
          </div>
        </div>

        <div class="right-section">
          <div class="calendar">
            <h3>Today's Date</h3>
            <div id="calendar-box" class="calendar-box"></div>
          </div>

          <div class="birthday">
            <h3>Today's Birthday</h3>
            <div class="birthday-box">
              <p>${studName} - ${studBirthDate}</p>
              <img src="data:image/jpeg;base64,${studPhoto}" alt="Photo" class="photo"/>
            </div>
          </div>
        </div>
      </section>

      <section class="gallery">
        <h3>Gallery</h3>
        <div class="gallery-box"></div>
      </section>
    </div>
  </main>
</div>

<script>
  // Sidebar toggle function
  function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const dashboard = document.getElementById("dashboard");
    sidebar.classList.toggle("show");
    dashboard.classList.toggle("shifted");
  }

  // Calendar date
  const today = new Date();
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  document.getElementById('calendar-box').innerText = today.toLocaleDateString('en-US', options);
</script>

</body>
</html>
