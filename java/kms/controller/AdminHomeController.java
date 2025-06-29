package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.dao.parentDAO;
import kms.dao.studentDAO;
import kms.dao.teacherDAO;
import kms.model.student;
import kms.service.QuoteService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class AdminHomeController
 */
@WebServlet("/AdminHomeController")
public class AdminHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 int adminId = (int) request.getSession().getAttribute("adminId");

		    request.setAttribute("adminName", teacherDAO.getTeacherNameByAdminId(adminId));
		    request.setAttribute("totalStudents", studentDAO.countAll());
		    request.setAttribute("totalTeachers", teacherDAO.countExcludingAdmin(adminId));
		    request.setAttribute("totalParents", parentDAO.countAll());
		    request.setAttribute("quote", QuoteService.getRandomQuote(adminId));

		    try {
		        request.setAttribute("birthdaysToday", studentDAO.getBirthdaysToday());
		    } catch (SQLException e) {
		        e.printStackTrace();
		        request.setAttribute("birthdaysToday", new ArrayList<>()); // empty list as fallback
		    }

		    request.getRequestDispatcher("adminHome.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
