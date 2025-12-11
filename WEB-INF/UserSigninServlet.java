
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
import javax.servlet.http.HttpSession;

public class UserSigninServlet extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String userId=request.getParameter("email");
        String pwd=request.getParameter("password");

        try {
            Connection con = DBUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE emailId=? AND password_hash=?");
            ps.setString(1, userId);
            ps.setString(2, pwd);

            ResultSet set = ps.executeQuery();

            if(set.next()){
               int id= set.getInt("userId");
                HttpSession session=request.getSession();
                session.setAttribute("userId", id);
                response.sendRedirect("Form.html");

            }else{
                out.println("invaild user id or password!");
                response.sendRedirect("signin.html");


            }


            ps.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }

    }
}
