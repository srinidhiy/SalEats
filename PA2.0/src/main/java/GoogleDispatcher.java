import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class GoogleDispatcher
 */
@WebServlet("/GoogleDispatcher")
public class GoogleDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/ProgrammingAssignment2";
    String user = "root";
    String pwd = "root";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        // check email in database
        String email = (String) request.getParameter("google-email");
        String name = (String) request.getParameter("google-name");
    	String sql = "SELECT * FROM User WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(sql);) {
    				ps.setString(1, email);
    				ResultSet rs = ps.executeQuery();
    				// it's in there --> redirect to login
    				if (rs.next()) {
    					String password = rs.getString("password");
    					request.setAttribute("email", email);
    					request.setAttribute("password", password);
    					request.getRequestDispatcher("LoginDispatcher").forward(request, response);
    					//response.sendRedirect("LoginDispatcher");
    				}
    				else {
    					// if email isn't there register new user - password can be anything
    					request.setAttribute("email", email);
    					request.setAttribute("name", name);
    					request.setAttribute("password", "+");
    					request.setAttribute("confirm", "+");
    					request.getRequestDispatcher("RegisterDispatcher").forward(request, response);
    				}

    				
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        
        
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
