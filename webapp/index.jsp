<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Al Kauthar Eduqids</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<!-- Navbar -->
<nav class="navbar">
  <div class="container">
    <div class="logo"><img src="${pageContext.request.contextPath}/image/logo.png" alt="Logo"></div>
    <ul class="menu">
      <li><a href="#home">Home</a></li>
      <li><a href="#about">About Us</a></li>
      <li class="dropdown">
        <a href="#programs">Our Programs</a>
        <ul class="submenu">
          <li><a href="#eip">Early Intervention Program</a></li>
          <li><a href="#preschool">Playschool & Preschool</a></li>
        </ul>
      </li>
      <li><a href="#branches">Our Branches</a></li>
      <li><a href="#contact">Contact Us</a></li>
    </ul>
    <a href="login.jsp" class="btn-cta">Login</a>
  </div>
</nav>

<!-- Hero -->
<header id="home" class="hero">
  <div class="hero-overlay"></div>
  <div class="hero-content">
    <h1>Welcome to Al Kauthar Eduqids</h1>
    <p>Your premier Islamic kindergarten for ages 1–6.</p>
    <a href="#about" class="btn">Learn More</a>
  </div>
</header>

<!-- About -->
<section id="about" class="about section">
  <div class="container">
    <h2>About Us</h2>
    <p>Founded in Sept 2013, serving ages 1–6 with Montessori, Pre‑Tahfiz, KAFA, and special needs programs.</p>
  </div>
</section>

<!-- Programs -->
<section id="programs" class="programs section">
  <div class="container">
    <h2>Our Programme</h2>
    <div class="card-grid">
      <div class="card" id="eip">
        <h3>Early Intervention Program (EIP)</h3>
        <p>2–12 yrs old — Therapist‑led sensory & speech support.</p>
      </div>
      <div class="card" id="preschool">
        <h3>Playschool & Preschool</h3>
        <p>Baby Program, Play School, Pre School for ages 0–6.</p>
      </div>
    </div>
  </div>
</section>

<!-- Counters -->
<section class="counter section">
  <div class="container">
    <div class="stats-grid">
      <div class="stat"><span>269</span><p>Centres</p></div>
      <div class="stat"><span>1000</span><p>Educators</p></div>
      <div class="stat"><span>10000</span><p>Students</p></div>
      <div class="stat"><span>20000</span><p>Parents</p></div>
    </div>
  </div>
</section>

<!-- Branches -->
<section id="branches" class="branches section">
  <div class="container">
    <h2>Our Branches</h2>
    <div class="branch-grid">
      <div class="branch">Selangor</div>
      <div class="branch">Kuala Lumpur</div>
      <div class="branch">Penang</div>
    </div>
  </div>
</section>

<!-- Contact -->
<section id="contact" class="contact section">
  <div class="container">
    <h2>Contact Us</h2>
    <p>Prime Jelutong, Bukit Jelutong, Shah Alam, Selangor<br>019-235 7244 · akesb.official@gmail.com</p>
  </div>
</section>

<!-- Footer -->
<footer class="footer">
  <p>&copy; 2025 Al Kauthar Eduqids. All Rights Reserved.</p>
</footer>

</body>
</html>