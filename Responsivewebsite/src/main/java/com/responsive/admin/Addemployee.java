package com.responsive.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class Addemployee extends HttpServlet {

    Connection con = null;
    PreparedStatement pstmt = null;

    String url = "jdbc:mysql://localhost:3306/responsivewebsite";
    String user = "root";
    String pswd = "root";

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

        	resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
          

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String location = req.getParameter("location");
            String tenth = req.getParameter("tenth");
            String twelfth = req.getParameter("twelfth");
            String graduation = req.getParameter("graduation");
            String experience = req.getParameter("experience");
            String department = req.getParameter("department");
            String role = req.getParameter("role");
            String salary = req.getParameter("salary");

            try {

                // Disable auto commit
                con.setAutoCommit(false);

                String query =
                    "INSERT INTO employee " +
                    "(name, email, phone, location, tenth_percent, twelfth_percent, graduation_percent, experience, department, role, salary) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                pstmt = con.prepareStatement(query);

                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.setString(4, location);
                pstmt.setDouble(5, Double.parseDouble(tenth));
                pstmt.setDouble(6, Double.parseDouble(twelfth));
                pstmt.setDouble(7, Double.parseDouble(graduation));
                pstmt.setDouble(8, Double.parseDouble(experience));
                pstmt.setString(9, department);
                pstmt.setString(10, role);
                pstmt.setDouble(11, Double.parseDouble(salary));

                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    con.commit();
                    resp.sendRedirect("sucess.html");   // 🔥 IMPORTANT
                }
                else {
                    con.rollback(); // ❌ Rollback if no rows inserted
                    out.println("<h3 style='color:red'>Failed to add employee</h3>");
                }

            } catch (Exception e) {
                try {
                    con.rollback();   // ❌ Rollback if exception occurs
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                out.println("<h3 style='color:red'>Transaction Rolled Back</h3>");
            }
        }

}
