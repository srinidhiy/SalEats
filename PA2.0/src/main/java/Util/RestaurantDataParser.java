package Util;


import com.google.gson.*;

import Util.Business.BusinessArray;
import Util.Business.Categories;
import Util.Business.Location;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that pretends to be the Yelp API
 */
public class RestaurantDataParser {
    private static Boolean ready = false;
    private static final String db = "jdbc:mysql://localhost:3306/ProgrammingAssignment2";
    static String user = "root";
    static String pwd = "root";

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     */
    public static void Init(String responseString) {
        if (ready) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO check if you've done the initialization
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;
        //TODO get businessHelper array from json
        
        // read in the file
        Gson gson = new Gson();
        String lines = "";
        String filename = "restaurant_data.json";
        try (InputStream inputStream = RestaurantDataParser.class.getResourceAsStream(filename);)
	    {
        	if (inputStream == null) {
        		System.out.println("file not found");
        	}
	    	Scanner in = new Scanner(inputStream);
				while (in.hasNext()) {
					lines += in.nextLine();
				}
	    	in.close();
	    } 
	    catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
	    	System.out.println("File not found");
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
	    	System.out.println("cannot open file");
		} 
        
//        System.out.println(lines);

        
        // create an array of businesses
        BusinessArray helper = new BusinessArray();
        helper = gson.fromJson(lines, BusinessArray.class);
        ArrayList<Business> businesses = new ArrayList<Business> () ;
        
	    for (int i = 0; i < helper.get_businesses().size(); i++) {
	    	if (helper.get_businesses().get(i) != null) {
//		    	System.out.println(helper.get_businesses().get(i).get_imageUrl());
//		    	System.out.println(helper.get_businesses().get(i).get_name());
//		    	System.out.println(helper.get_businesses().get(i).get_phone());
//		    	System.out.println(helper.get_businesses().get(i).get_price());
//		    	System.out.println(helper.get_businesses().get(i).get_rating());
//		    	System.out.println(helper.get_businesses().get(i).get_reviewCount());
//		    	System.out.println(helper.get_businesses().get(i).get_url());
//		    	System.out.println(helper.get_businesses().get(i).get_categories().get(0).to_string());
//		    	System.out.println(helper.get_businesses().get(i).get_location().to_string());
//		       	System.out.println("*****************");
		       	businesses.add(helper.get_businesses().get(i));
	    	}
	    }
        
        //TODO iterate the businessHelper array and insert every business into the DB
	    String restaurantDetails_sql = "INSERT INTO Restaurant_details (details_id, "
	    		+ "image_url, address, phone_no, estimated_price, yelp_url) VALUES (?, ?, ?, ?, ?, ?)";
	    String rating_sql = "INSERT INTO Rating_details (rating_id, review_count, rating) VALUES (?, ?, ?)";
	    String restaurant_sql = "INSERT INTO Restaurant (restaurant_id, restaurant_name, details_id, rating_id) VALUES (?, ?, ?, ?)";
	    String category_sql = "INSERT INTO Category (category_id, category_name, restaurant_id) VALUES (?, ?, ?)";
//	    String category_sql = "INSERT INTO Category (category_name, restaurant_id) VALUES (?, ?)";
	    String bridge_sql = "INSERT INTO Bridge_table (category_id, restaurant_id) VALUES (?, ?)";
//	    String bridge_sql = "INSERT INTO Bridge_table (restaurant_id) VALUES (?)";
	    int counter = 1;
	    
	    System.out.println("Starting insertions");
	    for (int i = 0; i < businesses.size(); i++) {
	    	// insert into restaurant_details table
	    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    			PreparedStatement ps = conn.prepareStatement(restaurantDetails_sql);) {
	    				Integer id = i;
	    				ps.setString(1, id.toString());
	    				ps.setString(2, businesses.get(i).get_imageUrl());
	    				ps.setString(3, businesses.get(i).get_location().to_string());
	    				ps.setString(4, businesses.get(i).get_phone());
	    				ps.setString(5, businesses.get(i).get_price());
	    				ps.setString(6, businesses.get(i).get_url());
	    				int row = ps.executeUpdate();
	    		} catch (SQLException e) {
					// TODO Auto-generated catch block
	    			System.out.println ("SQLException: " + e.getMessage());
	    		}	
	    	// insert into rating_details table
	    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    			PreparedStatement ps = conn.prepareStatement(rating_sql);) {
	    				Integer id = i;
	    				Integer review_count = businesses.get(i).get_reviewCount();
	    				Float rating = businesses.get(i).get_rating();
	    				ps.setString(1, id.toString());
	    				ps.setString(2, review_count.toString());
	    				ps.setString(3, rating.toString());
	    				int row = ps.executeUpdate();
	    		} catch (SQLException e) {
					// TODO Auto-generated catch block
	    			System.out.println ("SQLException: " + e.getMessage());
	    		}	
	    	// insert into restaurant table	    	
	    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    			PreparedStatement ps = conn.prepareStatement(restaurant_sql);) {
	    				Integer id = i;
	    				ps.setString(1, businesses.get(i).get_id());
	    				ps.setString(2, businesses.get(i).get_name());
	    				ps.setString(3, id.toString());
	    				ps.setString(4, id.toString());
	    				int row = ps.executeUpdate();
	    		} catch (SQLException e) {
					// TODO Auto-generated catch block
	    			System.out.println ("SQLException: " + e.getMessage());
	    		}	
	    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    		PreparedStatement ps = conn.prepareStatement(category_sql);) {
	    			Integer id = i;
    				ps.setString(1, id.toString());
    				String categories_list = "";
    				for (int j = 0; j < businesses.get(i).get_categories().size(); j++) {
    					categories_list += businesses.get(i).get_categories().get(j).get_title();
    					if (j < businesses.get(i).get_categories().size()-1) {
    						categories_list += ", ";
    					}
    				}
    				ps.setString(2, categories_list);
    				ps.setString(3, businesses.get(i).get_id());
    				int row = ps.executeUpdate();
	    		} catch (SQLException e) {
					// TODO Auto-generated catch block
	    			System.out.println ("SQLException: " + e.getMessage());
	    		}
//		    	// insert into bridge table
//		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
//		    			PreparedStatement ps = conn.prepareStatement(bridge_sql);) {
//		    				ps.setInt(1, counter++);
//		    				ps.setString(2, businesses.get(i).get_id());
//		    				int row = ps.executeUpdate();
//		    		} catch (SQLException e) {
//						// TODO Auto-generated catch block
//		    			System.out.println ("SQLException: " + e.getMessage());
//		    		}
	    	}
//	    	for (int j = 0; j < businesses.get(i).get_categories().size(); j++) {
//	    		String cat_name = businesses.get(i).get_categories().get(j).get_title();
//	    		String check_sql = "SELECT * FROM Category WHERE category_name = ?";
//	    		int cat_id = counter;
//	    		// check if category is already in category table - get its id
//	    		try (Connection conn = DriverManager.getConnection(db, user, pwd);
//		    			PreparedStatement ps = conn.prepareStatement(check_sql);) {
//		    				ps.setString(1, cat_name);
//		    				ResultSet rs = ps.executeQuery();
//		    				if (rs.next()) {
//		    					cat_id = rs.getInt("category_id");
//		    				}
//		    		} catch (SQLException e) {
//						// TODO Auto-generated catch block
//		    			System.out.println ("SQLException: " + e.getMessage());
//		    		}
//
//		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
//		    			PreparedStatement ps = conn.prepareStatement(category_sql);) {
//		    				ps.setInt(1, cat_id);
//		    				ps.setString(2, businesses.get(i).get_categories().get(j).get_title());
//		    				ps.setString(3, businesses.get(i).get_id());
//		    				int row = ps.executeUpdate();
//		    		} catch (SQLException e) {
//						// TODO Auto-generated catch block
//		    			System.out.println ("SQLException: " + e.getMessage());
//		    		}
//		    	// insert into bridge table
//		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
//		    			PreparedStatement ps = conn.prepareStatement(bridge_sql);) {
//		    				ps.setInt(1, cat_id);
//		    				ps.setString(2, businesses.get(i).get_id());
//		    				int row = ps.executeUpdate();
//		    		} catch (SQLException e) {
//						// TODO Auto-generated catch block
//		    			System.out.println ("SQLException: " + e.getMessage());
//		    		}
//		    	counter++;
//	    	}

	    	
	    	}

    public static Business getBusiness(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        //TODO return business based on id
        String sql = "SELECT * FROM Restaurant as r "
        		+ "INNER JOIN Restaurant_details as d on r.details_id = d.details_id "
        		+ "INNER JOIN Rating_details as a on r.rating_id = a.rating_id "
//        		+ "INNER JOIN Bridge_table as b on r.restaurant_id = b.restaurant_id "
        		+ "INNER JOIN Category as c on r.restaurant_id = c.restaurant_id "
        		+ "WHERE r.restaurant_id = ?";
        try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(sql);) {
    				ps.setString(1, id);
    				ResultSet rs = ps.executeQuery();
    				String name = "";
    				String imageUrl = "";
    	    		String url = "";
    	    		int reviewCount = 0;
    	    		ArrayList<Categories> categories = new ArrayList<Categories>(); 
    	    		float rating = 0;
    	    		String price = "";
    	    		String phone = "";
    	    		Location location = null;
    	    		Boolean exists = false;
    				while (rs.next()) {
    					name = rs.getString("restaurant_name");
    					imageUrl = rs.getString("image_url");
    					url = rs.getString("yelp_url");
    					reviewCount = rs.getInt("review_count");
    					Categories category = new Categories(rs.getString("category_name"));
    					categories.add(category);
    					rating = rs.getFloat("rating");
    					price = rs.getString("estimated_price");
    					phone = rs.getString("phone_no");
    					location = new Location(rs.getString("address"));
    					exists = true;
    				}
    				// ensures there was something returned
    				if (exists) {
    					Business b = new Business(id, name, imageUrl, url, reviewCount, categories, rating, price, phone, location);
    					return b;
    				}
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
    			System.out.println ("SQLException: " + e.getMessage());
    		}		

        return null;
    }

    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     */
    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) {
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO get list of business based on the param
        String sql = "";
        keyWord = keyWord.replace("\'", "\''");
        System.out.println("Keyword is " + keyWord);
        if (searchType.compareToIgnoreCase("name") == 0) {
        	// price sorting
        	if (sort.compareToIgnoreCase("price") == 0) {
	        	sql = "SELECT * FROM Restaurant as r "
	        			+ "INNER JOIN Restaurant_details as d "
	        			+ "ON r.details_id = d.details_id "
	        			+ "WHERE r.restaurant_name LIKE '%" + keyWord + "%' "
	        					+ "ORDER BY d.estimated_price ASC";
        	}
        	// review count sorting
        	if (sort.compareToIgnoreCase("review-count") == 0) {
        		sql = "SELECT * FROM Restaurant as r "
	        			+ "INNER JOIN Rating_details as d "
	        			+ "ON r.rating_id = d.rating_id "
	        			+ "WHERE r.restaurant_name LIKE '%" + keyWord + "%' "
	        					+ "ORDER BY d.review_count DESC";

        	}
        	// rating sorting
        	if (sort.compareToIgnoreCase("rating") == 0) {
        		sql = "SELECT * FROM Restaurant as r "
	        			+ "INNER JOIN Rating_details as d "
	        			+ "ON r.rating_id = d.rating_id "
	        			+ "WHERE r.restaurant_name LIKE '%" + keyWord + "%' "
	        					+ "ORDER BY d.rating DESC";
        	}

        }
        else {
        	// price sorting
        	if (sort.compareToIgnoreCase("price") == 0) {
	        	sql = "SELECT * FROM Category as c INNER JOIN Restaurant as r ON c.restaurant_id = r.restaurant_id"
	        			+ " INNER JOIN Restaurant_details as d ON r.details_id = d.details_id"
	        			+ " WHERE c.category_name LIKE '%" + keyWord + "%' "
	        			+ "ORDER BY d.estimated_price ASC";

        	}
        	// review count sorting
        	if (sort.compareToIgnoreCase("review-count") == 0) {
        		sql = "SELECT * FROM Category as c "
        				+ "INNER JOIN Restaurant as r ON c.restaurant_id = r.restaurant_id "
        				+ "INNER JOIN Rating_details as d ON r.rating_id = d.rating_id "
        				+ "WHERE c.category_name LIKE '%" + keyWord + "%' ORDER BY d.review_count DESC";
        	}
        	// rating sorting
        	if (sort.compareToIgnoreCase("rating") == 0) {
        		sql = "SELECT * FROM Category as c "
        				+ "INNER JOIN Restaurant as r ON c.restaurant_id = r.restaurant_id "
        				+ "INNER JOIN Rating_details as d ON r.rating_id = d.rating_id "
        				+ "WHERE c.category_name LIKE '%" + keyWord + "%' ORDER BY d.rating DESC";
        		}
        }
        RestaurantDataParser parser = new RestaurantDataParser();
        try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(sql);) {
    				ResultSet rs = ps.executeQuery();
    				while (rs.next()) {
    					Business b = parser.getBusiness(rs.getString("restaurant_id"));
    					businesses.add(b);
    				}
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
    			System.out.println ("SQLException: " + e.getMessage());
    		}	
        
        return businesses;

    }
}

//Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
class BusinessDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject();
        return new Gson().fromJson(content, type);
    }
}