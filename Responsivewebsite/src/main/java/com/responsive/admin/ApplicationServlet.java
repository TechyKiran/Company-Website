package com.responsive.admin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;



@MultipartConfig
public class ApplicationServlet extends HttpServlet {

    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/responsivewebsite",
                    "root",
                    "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        // ---- Read form data safely ----
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        String department = req.getParameter("department");
        String skills = req.getParameter("skills");
        String coverletter = req.getParameter("coverletter");

        // Experience (NULL-safe)
        String expParam = req.getParameter("experience");
        int experience = 0;
        if (expParam != null && !expParam.trim().isEmpty()) {
            experience = Integer.parseInt(expParam);
        }

        // ---- Resume upload ----
        Part resumePart = req.getPart("resume");
        String fileName = resumePart.getSubmittedFileName();

        String uploadPath = getServletContext().getRealPath("") + File.separator + "resumes";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        resumePart.write(uploadPath + File.separator + fileName);

        // ---- Insert into database ----
        try {
            String sql = "INSERT INTO job_application "
                    + "(name, email, phone, role, department, experience, skills, resume, coverletter) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, role);
            ps.setString(5, department);
            ps.setInt(6, experience);
            ps.setString(7, skills);
            ps.setString(8, fileName);
            ps.setString(9, coverletter);

            ps.executeUpdate();

            resp.getWriter().println(
            	    "<html>" +
            	    "<head>" +
            	    "<style>" +
            	    "body{font-family:Arial,Helvetica,sans-serif;background:#f4f6f8;text-align:center;padding-top:60px;}" +
            	    ".box{background:#ffffff;display:inline-block;padding:30px 40px;border-radius:10px;" +
            	    "box-shadow:0 4px 12px rgba(0,0,0,0.15);}" +
            	    "h2{color:#2c3e50;}" +
            	    "p{font-size:16px;color:#555;}" +
            	    "</style>" +
            	    "</head>" +
            	    "<body>" +
            	    "<div class='box'>" +
            	    "<h2>Dear " + name + ",</h2>" +
            	    "<p>Your application has been submitted successfully.</p>" +
            	    "<p>Our team will review your profile and contact you soon.</p>" +
            	    "</div>" +
            	    "</body>" +
            	    "</html>"
            	);


        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h3>Error while submitting application ❌</h3>");
        }
    }
}
