package com.rohith.service.employeeservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/OAuth/resource")
public class ResourceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Request Received");
		
		String userName = request.getParameter("userName").trim();
		if (userName == null || "".equals(userName)) {
			userName = "Guest";
		}
		
		String greetings = "Hello " + userName + "You have access";
		response.setContentType("text/plain");
		response.getWriter().write(greetings);
		
	}
	
}
