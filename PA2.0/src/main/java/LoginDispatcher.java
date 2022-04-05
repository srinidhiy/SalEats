import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/LoginDispatcher")
public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/ProgrammingAssignment2";
    String user = "root";
    String pwd = "root";
    
    public LoginDispatcher() {
    	
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String email = request.getParameter("login-email");
    	if (email == null) {
    		email = (String) request.getAttribute("email");
    	}
//    	System.out.println(email);
    	String password = (String) request.getParameter("login-password");
    	if (password == null) {
    		password = (String) request.getAttribute("password");
    	}

    	String sql = "SELECT * FROM User WHERE email = ?";
    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(sql);) {
    				ps.setString(1, email);
    				ResultSet rs = ps.executeQuery();
    				// this means that there is a user with this email in the database
    				if (rs.next()) {
    					// check if given password equals password in the database
    					if (rs.getString("password").compareTo(password) == 0) {
    						System.out.println("Logged in.");
    						
    						// format username to get rid of whitespace
    						String formattedName = rs.getString("name").replace(' ', '+');

    						// create a cookie with the user's username to display on home page
    						Cookie cookie = new Cookie("username", formattedName);
    						cookie.setMaxAge(60*60);
    						response.addCookie(cookie);
    						
    						// set logged-in to true, redirect to home page
    						request.setAttribute("logged-in", "true");
    						response.sendRedirect("WebContent/index.jsp");
    						
    					}
    					else {
    						System.out.println("Incorrect password.");
    						
    						request.setAttribute("error", "true");
    	   			    	request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	   			    	//response.sendRedirect("WebContent/auth.jsp");
    					}
    				}
    				else {
    					
    					// blank email spot
    					if (email.compareTo("") == 0) {
    						System.out.println("Missing email.");
    						request.setAttribute("error","true");
	    	   			    request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    						//response.sendRedirect("WebContent/auth.jsp");
    					}
    					else {
	    					// the email isn't registered
	    					System.out.println("Email is not registered.");
	    					
	    					request.setAttribute("error","true");
	    	   			    request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
	    					//response.sendRedirect("WebContent/auth.jsp");
    					}
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
