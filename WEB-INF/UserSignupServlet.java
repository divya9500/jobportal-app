
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class UserSignupServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        String mobNum = request.getParameter("mobNum");

        try {
            Connection con = DBUtil.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(fullName,emailId,password_hash,mobNum) VALUES (?, ?,?,?)"
            );
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, pwd);
            ps.setString(4, mobNum);

            ps.executeUpdate();
            ps.close();
            con.close();


            response.sendRedirect("signin.html");

        } catch (SQLIntegrityConstraintViolationException e) {

            out.println("<p style='color:red;'>Email already exists!</p>");

        }
        catch (Exception e) {
            out.println("Error: " + e.getMessage() );
        }
    }

}
