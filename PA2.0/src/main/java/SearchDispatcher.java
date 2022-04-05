import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Business;
import Util.Business.Categories;
import Util.RestaurantDataParser;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class SearchDispatcher
 */
@WebServlet("/SearchDispatcher")
public class SearchDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchDispatcher() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        // TODO get json file as stream, Initialize FakeYelpAPI by calling its initalize
        RestaurantDataParser parse = new RestaurantDataParser();
        parse.Init("hi");
        // method
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
    	String category = request.getParameter("search");
    	System.out.println("Category: " + category);
    	String search_input = request.getParameter("search-input");
    	System.out.println("Search input: " + search_input);
    	String sort_options = request.getParameter("sort-options");
    	// if they didn't select a sort option
    	if (sort_options == null) {
    		sort_options = "price";
    	}
    	System.out.println("Sort options: " + sort_options);
    	RestaurantDataParser parse = new RestaurantDataParser();
    	ArrayList<Business> businesses = new ArrayList<Business>();
    	businesses = parse.getBusinesses(search_input, sort_options, category);
    	
    	request.setAttribute("business-array", businesses);
    	request.setAttribute("category", category);
    	request.setAttribute("search-input", search_input);
    	//response.sendRedirect("WebContent/search.jsp");
    	request.getRequestDispatcher("WebContent/search.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}