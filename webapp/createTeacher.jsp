<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, kms.model.teacher" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Create Account - Teacher</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="css/createTeacher.css" />
</head>
<body>

 <div class="container">
    <div class="left-panel">
      <img src="images/sign up.png" alt="Kids" />
    </div>

    <div class="right-panel">
      <h2>Create Account</h2>
      <form action="CreateAccTController" method="post">
        
        <label>Full Name<span style="color: red;"> *</span></label>
        <input type="text" name="name" placeholder="Full name" required>

        <label>Email<span style="color: red;"> *</span></label>
        <input type="email" name="email" placeholder="abc@gmail.com" required>

        <div class="row1">
          <div class="role">
            <label>Role<span style="color: red;"> *</span></label><br>
            <select id="roleSelect" name="role" onchange="showTeacherType()" required>
              <option value="">Select Role</option>
              <option value="Teacher">Teacher</option>
              <option value="Admin">Admin</option>
            </select>
          </div>

          <div>
            <label>Phone Number<span style="color: red;"> *</span></label><br>
        <input type="tel" id="phone" name="phone" placeholder="011-1234567" maxlength="11" required> 
          </div>
        </div>

        <!-- Only show if Teacher is selected -->
        <div class ="row1">
        <div id="teacherType" class="hidden">
          <label>Teacher Type<span style="color: red;"> *</span></label> <br>
          <select name="teacherType">
            <option value="">Select Type</option>
            <option value="FullTime">Full-Time</option>
            <option value="PartTime">Part-Time</option>
          </select>
        </div>

        <!-- Optional Admin Assignment -->
        <div id="assignAdmin" class="hidden">
          <label>Admin</label> <br>
          <select name="adminId">
            <option value="">None</option>
            <c:forEach var="admin" items="${adminList}">
              <option value="${admin.teacherId}">${admin.teacherName}</option>
            </c:forEach>
          </select>
        </div>
        </div>

        <label>Password<span style="color: red;"> *</span></label>
		<input type="password" id="password" name="password" required>
		<small id="passwordHint" style="color: gray;"> * At least 8 characters, 1 uppercase, 1 lowercase, 1 number, 1 symbol.</small>
		<small id="passwordStrength" ></small>
        <label>Confirm Password<span style="color: red;"> *</span></label>
        <input type="password" id="confirmPassword" placeholder="Confirm password" required>

        <button type="submit">Create Account</button>

        <div class="login">
          Already have an account? <a href="#">Log in</a>
        </div>
      </form>
    </div>
  </div>
  
  <!--  <script src="createAcct.js"></script>-->

<script>
  function showTeacherType() {
    const role = document.getElementById("roleSelect").value;
    const teacherTypeDiv = document.getElementById("teacherType");
    const assignAdminDiv = document.getElementById("assignAdmin");

    if (role === "Teacher") {
      teacherTypeDiv.classList.remove("hidden");
      assignAdminDiv.classList.remove("hidden");
    } else if (role === "Admin") {
      teacherTypeDiv.classList.add("hidden");
      assignAdminDiv.classList.add("hidden");
    } else {
      teacherTypeDiv.classList.add("hidden");
      assignAdminDiv.classList.add("hidden");
    }
  }

  // Password match and strength check
  document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");
    const passwordStrength = document.getElementById("passwordStrength");

    // Validate passwords before form submission
    form.addEventListener("submit", function (e) {
      if (password.value !== confirmPassword.value) {
        e.preventDefault();
        alert("Passwords do not match!");
      }
    });

    // Password strength check
    password.addEventListener("input", function () {
      const val = password.value;
      let strength = "Weak";
      let color = "red";

      if (val.length >= 8 &&
        /[A-Z]/.test(val) &&
        /[a-z]/.test(val) &&
        /[0-9]/.test(val) &&
        /[^A-Za-z0-9]/.test(val)) {
        strength = "Strong";
        color = "green";
      } else if (val.length >= 6) {
        strength = "Medium";
        color = "orange";
      }

      passwordStrength.textContent = `Password strength: ${strength}`;
      passwordStrength.style.color = color;
    });
  });
</script>


</body>
</html>