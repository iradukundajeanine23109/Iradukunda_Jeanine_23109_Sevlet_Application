package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/myservlet")
public class MyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(MyServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        setupLogger();
    }

    private void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("myservlet.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File logger setup failed", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Use the form to submit your data.</h2>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_db", "postgres", "jeanine123");

            String sql = "INSERT INTO \"users\" (id, first_name, lasst_name) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.executeUpdate();

            sql = "SELECT * FROM \"users\" WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                out.println("<html><body>");
                out.println("<h2>Submitted Data:</h2>");
                out.println("<p>ID: " + rs.getString("id") + "</p>");
                
                char[] firstNameArray = rs.getString("first_name").toCharArray();
                out.println("<p>First Name: " + new String(firstNameArray) + "</p>");
                
                out.println("<p>Last Name: " + rs.getString("lasst_name") + "</p>");
                out.println("</body></html>");
            }

            stmt.close();
            conn.close();
            
            logger.info("Successfully processed request with ID: " + id);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database connection problem", e);
            out.println("Database connection problem: " + e.getMessage());
        }
    }
}
