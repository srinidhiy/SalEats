import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Constant;

import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/RegisterDispatcher")
public class RegisterDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
//    private static final String url = "jdbc:mysql://localhost:3306/PA4Users";
    
    private static final String db = "jdbc:mysql://localhost:3306/ProgrammingAssignment2";
    String user = "root";
    String pwd = "root";

    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
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
    	
    	String email = request.getParameter("register-email");
    	if (email == null) {
    		email = (String) request.getAttribute("email");
    	}
    	String name = request.getParameter("register-name");
    	if (name == null) {
    		name = (String) request.getAttribute("name");
    	}
    	String password = request.getParameter("register-password");
    	if (password == null) {
    		password = (String) request.getAttribute("password");
    	}
    	String confirm = request.getParameter("confirm-password");
    	if (confirm == null) {
    		confirm = (String) request.getAttribute("confirm");
    	}
    	String sql = "INSERT INTO User (email, name, password) VALUES (?, ?, ?)";
    	String email_check = "SELECT * FROM User WHERE email = ?";
    	String check = request.getParameter("terms");
    	Boolean err = false;
    	Boolean check_flag = false;
    	
    	// if there is anything that hasn't been filled out
		if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
			request.setAttribute("blank", "true");
			err = true;
			//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
		}
    	
		// checkbox not checked
//		if (check != null && check.compareToIgnoreCase("on")==0) {
//			check_flag = true;
//		}
//		if (!check_flag || check_flag == null) {
//			request.setAttribute("terms-error", "true");
//			err = true;
//		}
		
		// passwords aren't the same
		if (password.compareTo(confirm) != 0) {
			request.setAttribute("passwords-error", "true");
			err = true;
			//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
		}
		
		
		if (!email.isEmpty()) {
			
			// check if email format is correct
			String regex = "^(.+)@(\\S+)$";
			if (!email.matches(regex)) {
				request.setAttribute("email-format-error", "true");
				err = true;
			}
			else {
				// check if email is already registered
				try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    			PreparedStatement ps = conn.prepareStatement(email_check);) {
		    				ps.setString(1, email);
		    				ResultSet rs = ps.executeQuery();
		    				if (rs.next()) {
		    					request.setAttribute("duplicate-email", "true");
		    					err = true;
		    				}
		    		} catch (SQLException e) {
						// TODO Auto-generated catch block
		    			System.out.println ("SQLException: " + e.getMessage());
		    		}		
			}
		}
		
		if (!name.isEmpty()) {
			// check if the username is all characters except spaces
			String regex = "^[a-zA-Z][a-zA-Z\\s]+$";
			if (!name.matches(regex)) {
				request.setAttribute("name-format-error", "true");
				err = true;
			}
		}
		
    	if (err) {
    		//response.sendRedirect("WebContent/auth.jsp");
    		request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	}
    	else {
	    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    			PreparedStatement ps = conn.prepareStatement(sql);) {
	    				ps.setString(1, email);
	    				ps.setString(2, name);
	    				ps.setString(3, password);
	    				
	    				int row = ps.executeUpdate();
	
	    				//response.sendRedirect("WebContent/index.jsp");
	    				
	    		} catch (SQLException e) {
					// TODO Auto-generated catch block
	    			System.out.println ("SQLException: " + e.getMessage());
	    		}	
	    	
	    	// same as login
	    	String formattedName = name.replace(' ', '+');
			Cookie cookie = new Cookie("username", formattedName);
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			request.setAttribute("logged-in", "true");
			response.sendRedirect("WebContent/index.jsp");
		}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
