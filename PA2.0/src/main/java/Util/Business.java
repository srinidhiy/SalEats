package Util;

import java.util.ArrayList;
import java.util.Map;

/**
 * The class used to model a business
 */
public class Business {
    

    public Business(String _id, String _name, String _imageUrl, 
    		String _url, int _reviewCount, ArrayList<Categories> _categories, 
    		float _rating, String _price, String _phone, Location _location) {
        //TODO Initialization code
    	this.id = _id;
    	this.name = _name;
    	this.image_url = _imageUrl;
    	this.url = _url;
    	this.review_count = _reviewCount;
    	this.categories = _categories;
    	this.rating = _rating;
    	this.price = _price;
    	this.phone = _phone;
    	this.location = _location;
    }
    
    private String id;
    public String get_id() {
    	return id;
    }
//    private String alias;
//    private String get_alias() {
//    	return alias;
//    }
    private String name;
    public String get_name() {
    	return name;
    }
    private String image_url;
    public String get_imageUrl() {
    	return image_url;
    }

//    private Boolean is_closed;
//    private Boolean get_isClosed() {
//    	return is_closed;
//    }

    private String url;
    public String get_url() {
    	return url;
    }

    private int review_count;
    public int get_reviewCount() {
    	return review_count;
    }

    private ArrayList<Categories> categories;
    public ArrayList<Categories> get_categories() {
    	return categories;
    }

    private float rating;
    public float get_rating() {
    	return rating;
    }

//    private ArrayList<String> transactions;
//    private ArrayList<String> get_transactions() {
//    	return transactions;
//    }

    private String price;
    public String get_price() {
    	return price;
    }

    private String phone;
    public String get_phone() {
    	return phone;
    }
    
    private Location location;
    public Location get_location() {
    	return location;
    }
//    private String display_phone;
//    private String get_displayPhone() {
//    	return display_phone;
//    }

//    private Float distance;
//    private Float get_distance() {
//    	return distance;
//    }

    public static class Categories {
    	public Categories(String name) {
    		this.title = name;
    	}
    	private String alias;
    	private String title;
    	public String get_alias() {
    		return alias;
    	}
    	public String get_title() {
    		return title;
    	}
    	public String to_string() {
    		return title;
    	}
    }

    public static class Location {
    	public Location (String name) {
    		this.display_name = name;

    	}
    	private String display_name;
    	public String get_displayName() {
    		return display_name;
    	}
    	private ArrayList<String> display_address;
    	public ArrayList<String> get_displayAddress() {
    		return display_address;
    	}
    	public String to_string() {
    		 String hi = "";
    		 for (String addy : display_address) {
    			 hi += addy;
    			 hi += " ";
    		 }
    		 return hi;
    	}
    	
    }
    
//    static class LocationHelper {
//    	public LocationHelper(String name) {
//    		this.display_name = name;
//    	}
//    	private String display_name;
//    	public String get_displayName() {
//    		return display_name;
//    	}
//    }
    
    static class BusinessArray {
    	private ArrayList<Business> businesses;
    	public ArrayList<Business> get_businesses() {
    		return businesses;
    	}
    }
    
    
    

    //TODO Add Getters (No Setters as the business does not change in our assignment once constructed)
}

