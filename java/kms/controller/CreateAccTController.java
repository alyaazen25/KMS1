package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kms.dao.teacherDAO;
import kms.model.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CreateAccTController")
public class CreateAccTController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateAccTController() {
        super();
    }

    // Load form with admin list
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<teacher> adminList = teacherDAO.getAllAdmins(); // fetch all admins
        request.setAttribute("adminList", adminList);        // send to JSP
        RequestDispatcher rd = request.getRequestDispatcher("createTeacher.jsp");
        rd.forward(request, response);
    }

    // Handle form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name     = request.getParameter("name");
        String email    = request.getParameter("email");
        String pass     = request.getParameter("password");
        String role     = request.getParameter("role");
        String phone    = request.getParameter("phone");
        String type     = request.getParameter("teacherType"); // might be null if Admin
        String adminIdParam = request.getParameter("adminId");

        teacher teach;

        // ✅ Force Admin to FullTime
        if ("Admin".equalsIgnoreCase(role)) {
            fullTime ft = new fullTime();
            teach = ft;
            type = "FullTime";
        } else if ("FullTime".equalsIgnoreCase(type)) {
            fullTime ft = new fullTime();
            teach = ft;
        } else if ("PartTime".equalsIgnoreCase(type)) {
            partTime pt = new partTime();
            teach = pt;
        } else {
            // fallback
            teach = new teacher();
        }

        // ✅ Set fields
        teach.setTeacherName(name);
        teach.setTeacherEmail(email);
        teach.setTeacherPass(pass);
        teach.setTeacherPhone(phone);
        teach.setTeacherRole(role);
        teach.setTeacherType(type);

        // ✅ Handle adminId
        if (!"Admin".equalsIgnoreCase(role)) {
            if (adminIdParam != null && !adminIdParam.isEmpty()) {
                teach.setAdminId(Integer.parseInt(adminIdParam));
            } else {
                teach.setAdminId(null);
            }
        } else {
            teach.setAdminId(null); // Admin has no adminId
        }

        // ✅ Log what is actually sent
        System.out.println("Role: " + role);
        System.out.println("Type: " + type);
        System.out.println("Teach instanceof fullTime: " + (teach instanceof fullTime));
        System.out.println("Teach instanceof partTime: " + (teach instanceof partTime));
        System.out.println("Teach class: " + teach.getClass().getSimpleName());

        // ✅ Insert
        teacherDAO.addTeacher(teach, type);

        request.setAttribute("teachers", teacherDAO.getAllTeacher());
        RequestDispatcher req = request.getRequestDispatcher("login.jsp");
        req.forward(request, response);
    }

}
