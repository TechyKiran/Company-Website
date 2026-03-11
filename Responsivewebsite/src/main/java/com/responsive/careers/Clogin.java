package com.responsive.careers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Clogin extends HttpServlet {
	
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
		resp.setContentType("text/html");
		
		String email = req.getParameter("Email");
		String pass = req.getParameter("Password");
		
		try
		{
			String Query = "select * from careerregistration where email=? and pswd =?";
			pstmt=con.prepareStatement(Query);
			
			pstmt.setString(1, email);
			pstmt.setString(2, pass);
			
			res = pstmt.executeQuery();
			
			if(res.next()==true)
			{
				req.getRequestDispatcher("Careerloged.html").forward(req, resp);
//				writer.println("Login Successfully");
			}
			else
			{
				writer.println("Login failure");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
