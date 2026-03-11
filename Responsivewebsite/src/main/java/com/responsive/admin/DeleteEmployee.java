package com.responsive.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class DeleteEmployee extends HttpServlet {
	
	Connection con = null;
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
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		System.out.println(id);
		try {

			String query = "DELETE FROM employee WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);

			int rows = pstmt.executeUpdate();

			if (rows > 0) {
				writer.println("Deleted Successfully!!!!!!");
			    System.out.println("Employee deleted successfully");
			} else {
				writer.println("The Record not found??");
			    System.out.println("No employee found with given ID");
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
