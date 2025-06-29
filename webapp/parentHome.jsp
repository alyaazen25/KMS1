<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    kms.model.parent parentUser = (kms.model.parent) session.getAttribute("user");
    String parentName = "";
    int parentId = 0;

    if (parentUser != null) {
        parentName = parentUser.getParentName();
        parentId = parentUser.getParentId();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Parent Homepage - Al Kauthar Eduqids</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/teacherHome.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>
<body>

<!-- Header -->
<header>
  <button class="navSidebar" onclick="toggleSidebar()">
    <i class="fa-solid fa-bars"></i>
  </button>
  <div class="navbar">
    <img src="image/logo.png" alt="ALKAUTHAR EDUQIDS Logo" />
  </div>
</header>

<!-- Sidebar -->
<nav class="sidebar" id="sidebar">
  <div class="profile">
    <a href="viewAccount.jsp">
      <img src="DisplayParentImage?id=<%= parentId %>" alt="Parent Profile Photo" />
    </a>
    <h3><%= parentName %></h3>
    <p>Parent</p>
  </div>
  <ul class="sidebar-links top-links">
    <li><a href="parentHome.jsp" class="active"><i class="fas fa-home"></i> Home</a></li>
    <li><a href="ListChooseSubjectController"><i class="fas fa-book"></i> Subject</a></li>
    <li><a href="#"><i class="fas fa-calendar-check"></i> Attendance</a></li>
    <li><a href="#"><i class="fas fa-chart-line"></i> Progress</a></li>
    <li><a href="ListStudentController"><i class="fas fa-child"></i> Student</a></li>
  </ul>
  <div class="_separator_1e1cy_1"></div>
  <ul class="sidebar-links bottom-links">
    <li><a href="#"><i class="fas fa-user-cog"></i> Account</a></li>
    <li><a href="LogoutController"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
  </ul>
</nav>

<!-- Main Dashboard -->
<div id="dashboard" class="dashboard">
  <main>
    <div class="container">

      <!-- Welcome Section -->
      <section class="welcome-box">
        <div class="welcome-text">
          <h2>Hi, <%= parentName %></h2>
          <p>Every child entrusted to your care is a trust from Allah. <br/>
             Through your kindness and wisdom, you are shaping the future of the ummah with love and barakah.</p>
        </div>
        <div class="welcome-image">
          <img src="images/parents.png" alt="Parent Icon">
        </div>
      </section>

      <!-- Student Cards (Repeat per student) -->
      <section class="stats">
        <div class="stat pink"><i class="fas fa-book"></i><h3>3</h3><p>Subjects</p></div>
        <div class="stat green"><i class="fas fa-calendar-check"></i><h3>Present</h3><p>8:00 AM</p></div>
        <div class="stat pink"><i class="fas fa-chart-line"></i><h3>Progress</h3><p>Available</p></div>
      </section>

      <hr/>

      <!-- Quotes, Tips, Calendar -->
      <section class="main-section">
        <div class="left-section">
          <div class="quotes">
            <h3>Quotes</h3>
            <div class="quote-box">${randomQuote}</div>
          </div>

          <div class="tips">
            <h3>Parenting Tips</h3>
            <p>Be present, listen actively, and lead by example.</p>
          </div>

          <div class="buttons">
            <div class="btn-row">
              <a href="ChooseSubject.jsp" class="btn"><i class="fas fa-book-open"></i><p>Register Subject</p></a>
              <a href="#" class="btn"><i class="fas fa-eye"></i><p>View Progress</p></a>
            </div>
          </div>
        </div>

        <div class="right-section">
          <div class="calendar">
            <h3>Today's Date</h3>
            <div id="calendar-box" class="calendar-box"></div>
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

<!-- JavaScript -->
<script>
  function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
    document.getElementById("dashboard").classList.toggle("shifted");
  }

  const today = new Date();
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  document.getElementById('calendar-box').innerText = today.toLocaleDateString('en-US', options);
</script>

</body>
</html>
