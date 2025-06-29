package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import kms.dao.*;
import kms.model.*;

/**
 * Servlet implementation class loginController
 */
@WebServlet("/loginController")
public class loginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public loginController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        if (role.equalsIgnoreCase("Parent")) {
            parent p = parentDAO.validate(email, password);
            if (p != null) {
                session.setAttribute("user", p);
                session.setAttribute("role", "parent");
                session.setAttribute("parentId", p.getParentId());
                session.setAttribute("parentName", p.getParentName()); // ✅ Added
                response.sendRedirect("parentHome.jsp");
            } else {
                response.sendRedirect("login.jsp?msg=fail");
            }

        } else if (role.equalsIgnoreCase("Teacher")) {
            teacher teach = teacherDAO.validate(email, password);
            if (teach != null && !"admin".equalsIgnoreCase(teach.getTeacherRole())) {
                session.setAttribute("user", teach);
                session.setAttribute("role", "teacher");
                session.setAttribute("teacherId", teach.getTeacherId());
                session.setAttribute("teacherName", teach.getTeacherName()); // ✅ Added
                response.sendRedirect("teacherHome.jsp");
            } else {
                response.sendRedirect("login.jsp?msg=fail");
            }

        } else if (role.equalsIgnoreCase("Admin")) {
            teacher admin = teacherDAO.validate(email, password);
            if (admin != null && "admin".equalsIgnoreCase(admin.getTeacherRole())) {
                session.setAttribute("user", admin);
                session.setAttribute("role", "admin");
                session.setAttribute("adminId", admin.getAdminId());
                session.setAttribute("adminName", admin.getTeacherName()); // ✅ Added
                response.sendRedirect("adminHome.jsp");
            } else {
                response.sendRedirect("login.jsp?msg=fail");
            }

        } else {
            response.sendRedirect("login.jsp?msg=fail");
        }
    }
}
