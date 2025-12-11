
import java.io.*;
import java.io.File;
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
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;




@MultipartConfig
public class JobApplicationServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int jobId=2;

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String mobNum = request.getParameter("mobNum");

        String degree = request.getParameter("degree");
        String year = request.getParameter("year");

        String gender = request.getParameter("gender");

        String noOfYear = request.getParameter("noOfYear");
        String curEmp = request.getParameter("curEmp");
        String curCTC = request.getParameter("curCTC");
        String noticePeriod = request.getParameter("noticePeriod");
        String expCTC = request.getParameter("expCTC");

        String skill = request.getParameter("skill");
        String currLocation = request.getParameter("currLocation");
        String preLocation = request.getParameter("preLocation");

        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String SAVE_DIR = "C:/upload/";
        File fileSaveDir = new File(SAVE_DIR);


        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String filePath = SAVE_DIR + fileName;
        filePart.write(filePath);

        String  uniqueFileName= userId + "_" + System.currentTimeMillis() + "_" + fileName;



        try {
            Connection con = DBUtil.getConnection();;

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO job_applications(userId,jobId,firstName,lastName,emailId,mobNum,gender" +
                            ",degree,graduationYear," +
                            "currentEmployer,currentCTC,noticePeriod,expectedCTC,skills," +
                            "currentLocation,preferredLocation,resumeFilePath) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, email);
            ps.setString(6, mobNum);
            ps.setString(7, gender);
            ps.setString(8, degree);
            ps.setString(9, year);
            ps.setString(10, curEmp);
            ps.setString(11, curCTC);
            ps.setString(12, noticePeriod);
            ps.setString(13,expCTC);
            ps.setString(14, skill);
            ps.setString(15, currLocation);
            ps.setString(16, preLocation);

            ps.setString(17,filePath+"/"+uniqueFileName);

            ps.executeUpdate();
            ps.close();
            con.close();


            response.sendRedirect("Form.html");

        }
        catch (Exception e) {
            out.println("Error: " + e.getMessage() );
        }
    }

}
