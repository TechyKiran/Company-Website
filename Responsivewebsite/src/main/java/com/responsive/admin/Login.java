package com.responsive.admin;

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


public class Login extends HttpServlet {

	Connection con = null;
	PreparedStatement pstmt=null;
	String url="jdbc:mysql://localhost:3306/responsivewebsite";
	String user="root";
	String pswd="root";
	ResultSet res = null;
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
    		con =DriverManager.getConnection(url,user,pswd);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
PrintWriter writer = resp.getWriter();
    	
    	String name = req.getParameter("Username");
    	String pwd = req.getParameter("Password");


    	
    	try
    	{
    		String query = "select * from login where name=? and pswd=?";
    		pstmt = con.prepareStatement(query);
    		
    		pstmt.setString(1, name);
    		pstmt.setString(2, pwd);
    		
    		res=pstmt.executeQuery();
    		
    		if(res.next()==true)
    		{
//    			writer.println("Successfully logedin!!!");
    			req.getRequestDispatcher("adminhome.html").forward(req, resp);
    		}
    		else
    		{
    			writer.println("Username and password are not correct!!!!");
    		}	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}

