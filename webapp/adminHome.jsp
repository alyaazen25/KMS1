<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Admin Homepage - Al Kauthar EduQids</title>
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
      <img src="images/admin.jpg" alt="Admin Profile Photo" />
    </a>
    <h3>${adminName}</h3>
    <p>Admin</p>
  </div>
  <ul class="sidebar-links top-links">
    <li><a href="adminHome.jsp"><i class="fas fa-home"></i> Home</a></li>
    <li><a href="ListSubjectController"><i class="fas fa-book"></i> Subject</a></li>
    <li><a href="recordAttendance.jsp"><i class="fas fa-calendar-check"></i> Attendance</a></li>
    <li><a href="recordProgress.jsp"><i class="fas fa-chart-line"></i> Progress</a></li>
    <li><a href="createStudent.jsp"><i class="fas fa-user-graduate"></i> Student</a></li>
    <li><a href="createParent.jsp"><i class="fas fa-user"></i> Parent</a></li>
    <li><a href="createTeacher.jsp"><i class="fas fa-chalkboard-teacher"></i> Teacher</a></li>
  </ul>
  <div class="_separator_1e1cy_1"></div>
  <ul class="sidebar-links bottom-links">
    <li><a href="viewAccount.jsp"><i class="fas fa-user-cog"></i> Account</a></li>
    <li><a href="logout.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
  </ul>
</nav>

<div class="dashboard" id="dashboard">
<main>
  <div class="container">
    <section class="welcome-box">
      <div class="welcome-text">
        <h2>Hi, ${adminName}</h2>
        <p>Every child entrusted to your care is a trust from Allah. <br/>
           Through your leadership, you are building a generation with knowledge and barakah.</p>
      </div>
      <div class="welcome-image">
        <img src="images/admins.png" alt="Admin Illustration" />
      </div>
    </section>

    <section class="stats">
      <div class="stat green">
        <i class="fas fa-user-graduate"></i>
        <h3>${totalStudents}</h3>
        <p>Students</p>
      </div>
      <div class="stat pink">
        <i class="fas fa-chalkboard-teacher"></i>
        <h3>${totalTeachers}</h3>
        <p>Teachers</p>
      </div>
      <div class="stat green">
        <i class="fas fa-users"></i>
        <h3>${totalParents}</h3>
        <p>Parents</p>
      </div>
    </section>

    <hr/>

    <section class="main-section">
      <div class="left-section">
        <div class="quotes">
          <h3>Quotes</h3>
          <div class="quote-box">${quote}</div>
        </div>

        <div class="buttons">
          <div class="btn-row">
            <a href="createStudent.jsp" class="btn"><i class="fas fa-user-plus"></i><p>Create Student</p></a>
            <a href="ListSubjectController" class="btn"><i class="fas fa-book"></i><p>Create Subject</p></a>
          </div>
          <div class="btn-row">
            <a href="recordAttendance.jsp" class="btn"><i class="fas fa-check-square"></i><p>Record Attendance</p></a>
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
          <c:if test="${empty birthdaysToday}">
            <div class="birthday-box">
              <p>No birthdays today</p>
            </div>
          </c:if>
          <c:forEach var="stud" items="${birthdaysToday}">
            <div class="birthday-box">
              <p>${stud.studName} - 
                 <fmt:formatDate value="${stud.studBirthDate}" pattern="dd MMM yyyy"/></p>
              <img src="data:image/jpeg;base64,${stud.base64Photo}" alt="Photo" class="photo"/>
            </div>
          </c:forEach>
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
  function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const dashboard = document.getElementById("dashboard");
    sidebar.classList.toggle("show");
    dashboard.classList.toggle("shifted");
  }

  const today = new Date();
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  document.getElementById('calendar-box').innerText = today.toLocaleDateString('en-US', options);
</script>

</body>
</html>
