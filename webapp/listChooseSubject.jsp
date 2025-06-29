<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Choose Subject</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- External CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listChooseSubject.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

    <script>
        function toggleSidebar() {
            document.getElementById("sidebar").classList.toggle("active");
        }

        function toggleDropdown(id) {
            document.getElementById(id).classList.toggle("show");
        }

        window.addEventListener('click', function(e) {
            document.querySelectorAll(".dropdown-content").forEach(dc => {
                if (!dc.contains(e.target) && !dc.previousElementSibling.contains(e.target)) {
                    dc.classList.remove("show");
                }
            });
        });
    </script>
</head>
<body>

<!-- Header -->
<header>
    <button class="toggle-btn" onclick="toggleSidebar()">
        <i class="fa-solid fa-bars"></i>
    </button>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/image/logo.png" alt="Logo">
    </div>
</header>

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
    <div class="profile">
        <img src="${pageContext.request.contextPath}/image/admin.jpg" alt="Admin Profile">
        <h3>Nurul Najah Binti Wahab</h3>
        <p>Administrator</p>
    </div>

    <a href="#">Dashboard</a>
    <a href="CreateSubjectController">Create Subject</a>

    <div class="dropdown">
        <a href="javascript:void(0)" onclick="toggleDropdown('studentDropdown')">
            Students <i class="fa fa-caret-down"></i>
        </a>
        <div class="dropdown-content" id="studentDropdown">
            <a href="#">View Students</a>
            <a href="#">Edit Students</a>
        </div>
    </div>

    <a href="#">Teachers</a>
    <a href="#">Accounts</a>
    <a href="#">Logout</a>
</div>

<!-- Main Content -->
<div class="container">
    <!-- Header Row -->
    <div class="header-row">
        <h2>Choose Subject</h2>
        <form action="AddChooseSubjectController" method="get">
            <input type="hidden" name="studId" value="${selectedStudId}" />
            <button type="submit" class="btn-add">+ Register New Subject</button>
        </form>
    </div>

    <!-- Student Dropdown -->
    <form action="ListChooseSubjectController" method="get">
        <div class="form-row">
            <label>Student</label>
            <select name="studId" onchange="this.form.submit()" required>
                <option value="">Select student</option>
                <c:forEach var="student" items="${students}">
                    <option value="${student.studId}" 
                        <c:if test="${student.studId == selectedStudId}">selected</c:if>>
                        ${student.studName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>

    <!-- Subject Grid -->
    <div class="subject-grid">
        <c:choose>
            <c:when test="${empty subjectList}">
                <div class="placeholder">
                    No subjects registered or available for this student.
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="subject" items="${subjectList}">
                    <div class="subject-card">
                        <p><i class="fa-solid fa-book"></i> ${subject.subjectName}</p>
                        <p><small>Teacher ID: ${subject.teacherId}</small></p>

                        <c:choose>
                            <c:when test="${registeredSubjectIds.contains(subject.subjectId)}">
                                <span class="status registered">Registered</span>
                                <form action="DeleteChooseSubjectController" method="post">
                                    <input type="hidden" name="studId" value="${selectedStudId}" />
                                    <input type="hidden" name="subjectId" value="${subject.subjectId}" />
                                    <button type="submit" class="btn-delete">Delete</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="RegisterSubjectController" method="post">
                                    <input type="hidden" name="studId" value="${selectedStudId}" />
                                    <input type="hidden" name="subjectId" value="${subject.subjectId}" />
                                    <button type="submit" class="btn-register">Register</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
