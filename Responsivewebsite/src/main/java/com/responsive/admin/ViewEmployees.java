package com.responsive.admin;

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


public class ViewEmployees extends HttpServlet {
	
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
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			String query="select * from employee";
			Statement stmt = con.createStatement();
			res = stmt.executeQuery(query);
			
			writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>Employee Details</title>");
	        writer.println("<style>");
	        writer.println("table { border-collapse: collapse; width: 70%; }");
	        writer.println("th, td { border: 1px solid black; padding: 8px; text-align: center; }");
	        writer.println("th { background-color: #f2f2f2; }");
	        writer.println("</style>");
	        writer.println("</head>");
	        writer.println("<body>");

	        writer.println("<center>");
	        writer.println("<h3><mark>Employee details as follows:</mark></h3>");
	        writer.println("<table>");
	        writer.println("<tr>");
	        writer.println("<th>Id</th>");
	        writer.println("<th>Name</th>");
	        writer.println("<th>Email</th>");
	        writer.println("<th>Phone Number</th>");
	        writer.println("<th>Location</th>");
	        writer.println("<th>10th</th>");
	        writer.println("<th>12th</th>");
	        writer.println("<th>Graduation</th>");
	        writer.println("<th>Experience</th>");
	        writer.println("<th>Department</th>");
	        writer.println("<th>Role</th>");
	        writer.println("<th>Salary</th>");
	        writer.println("</tr>");

	        while (res.next()) {
	            writer.println("<tr>");
	            writer.println("<td>" + res.getInt(1) + "</td>");
	            writer.println("<td>" + res.getString(2) + "</td>");
	            writer.println("<td>" + res.getString(3) + "</td>");
	            writer.println("<td>" + res.getString(4) + "</td>");
	            writer.println("<td>" + res.getString(5) + "</td>");
	            writer.println("<td>" + res.getString(6) + "</td>");
	            writer.println("<td>" + res.getString(7) + "</td>");
	            writer.println("<td>" + res.getString(8) + "</td>");
	            writer.println("<td>" + res.getString(9) + "</td>");
	            writer.println("<td>" + res.getString(10) + "</td>");
	            writer.println("<td>" + res.getString(11) + "</td>");
	            writer.println("<td>" + res.getString(12) + "</td>");
	            writer.println("</tr>");
	        }

	        writer.println("</table>");
	        writer.println("</center>");

	        writer.println("</body>");
	        writer.println("</html>");
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
