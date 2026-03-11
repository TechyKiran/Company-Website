package com.responsive.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class Employeelogin extends HttpServlet {
	
	Connection con =null;
	PreparedStatement pstmt = null;
	ResultSet res = null;
	String url = "jdbc:mysql://localhost:3306/responsivewebsite";
	String user = "root";
	String pswd ="root";
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pswd);			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		
		String emp = req.getParameter("Email");
		String pswd = req.getParameter("Password");
		
		try {
			String query="Select * from registration where email=? and password=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, emp);
			pstmt.setString(2, pswd);
			
			res = pstmt.executeQuery();
			
			if(res.next()==true)
			{	
				req.setAttribute("email", emp);
				req.getRequestDispatcher("/Edetails").include(req, resp);
//				writer.println("Logged in successfully");
			}
			else
			{
				writer.println("Please contact the Admin!!!!");
			}
			
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
