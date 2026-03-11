package com.responsive.careers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Register extends HttpServlet {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String name = req.getParameter("FullName");
		String email = req.getParameter("Email");
		String phone = req.getParameter("Phone");
		String password = req.getParameter("Password");
		
		try {
			String query ="INSERT INTO careerregistration (name, email, phone, pswd) VALUES (?, ?, ?, ?)";;
			pstmt=con.prepareStatement(query);
			
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, password);
			
			int rows = pstmt.executeUpdate();
			if (rows > 0) 
	        {
	            writer.println("Registration Successful");
	        } 
	        else {
	            writer.println("Registration Failed");
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
