<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" href="/PA2.0/WebContent/index.css">
    <script src="https://kit.fontawesome.com/3204349982.js"
            crossorigin="anonymous"></script>

	<%@ page import="java.util.ArrayList" %>
	<%@ page import="Util.Business" %>
	<%@ page import="Util.RestaurantDataParser" %>
    <%
    	
        //TODO perform search
        String business_id = (String) request.getParameter("busid");
    	System.out.println(business_id);
    	RestaurantDataParser parse = new RestaurantDataParser();
    	Business b = parse.getBusiness(business_id);
    	System.out.println(b.get_name());
    	System.out.println(b.get_location().get_displayName());

        //TODO check if logged in
        Boolean registered = false;
        int id = 0;
        Cookie cookies[] = request.getCookies();
        if (cookies != null && !cookies[0].getValue().equals("")) {
        	for (int i = 0; i < cookies.length; i++) {
        		String formattedName = ((String) cookies[i].getName()).replace("+", " ");
        		if (formattedName.compareTo("username") == 0) {
        			registered = true;
        			id = i;
        		}
        	}
        }
        
        if ((String) request.getAttribute("logged-out") != null) {
        	registered = false;
        }

    %>
</head>

<style>
     

        #container {
            align-items: center;
            justify-content: center;
/*            display: flex;*/
        }

        #container select {
            margin-left: 50px;
        }

        #container input[type=text] {
            width: 50%;
            border: 1px solid gainsboro;
            border-radius: 2px;
        }

        #container button {
            background-color: crimson;
            color: white;
            border-color: none;
            border-radius: 4px;
            width: 45px;
            height: 25px;
            font-weight: 300;
            border: none;
            margin-left: 20px;
        }

        #container input[type=radio] {
            margin-left: 50px;
        }

        input[type=radio] {
            margin-left: 50px;
        }
        
        span {
        	display: block;
        }
        
        #results {
       		margin-top: 40px;
       		margin-bottom: 15px;
       		margin-left: 50px;
        }
        
        #business {
        	margin-left: 50px;
        	display: flex;
        }
        
        #business-image img{
        	width: 200px;
        	height: 200px;
        	object-fit: cover;
        	padding: 20px;
        	border-radius: 30px;
        }
        
        #business-info {
        	padding-top: 20px;
        }
        
        
        #business-info p {
        	padding-left: 30px;
        }
        
  


    </style>
<body>
	<div id="navigation-bar">
         <div id="saleats">
             <a href="/PA2.0/WebContent/index.jsp">SalEats!</a>
             
             <%
             	if (registered) {
             		String formattedUsername = cookies[id].getValue().replace("+", " ");
             		out.println("\tHello, " + formattedUsername + "!");
             	}
             
             %>
             
         </div>
         

         <div id="navigation">
             <a href="/PA2.0/WebContent/index.jsp"> Home </a>
             
              <%
             	if (registered) {
             		out.println("<a href=\"/PA2.0/LogoutDispatcher\"> Logout </a>");
             	}
             	else {
             		out.println("<a href=\"/PA2.0/WebContent/auth.jsp\"> Login / Register </a>");
             	}
             
             %>
         </div>
     </div>
     
     <br>

	<div id="container">
            <form action = "/PA2.0/SearchDispatcher" method = "GET" id = "search-options">
            
            <div id = "search-bar">
                <select name="search" id="search">
                    <option>Category</option>
                    <option>Name</option>
                </select>
                
                <input type = "text" name = "search-input">

                <button type="submit" id = "search-button"><i class='fa fa-search'></i></button>


           <!--  <div id = "radio-buttons"> -->
           		
                <input type = "radio" id = "price" name = "sort-options" value = "price"> 
                <label for="price">Price</label>

                <input type = "radio" id = "review-count" name = "sort-options" value = "review-count"> 
                <label for="review-count">Review Count</label>
				
                <input type = "radio" id = "rating" name = "sort-options" value = "rating"> 
                <label for="rating">Rating</label>
                
            </div>
            
            </form>
        </div>
        
        <br><br>
        
      <div id = "results">
      	<%
      		out.println("<h2> " + b.get_name() + "</h2>");
      	%>
      
      </div>
		
		<div id = "business">
			<div id = "business-image">
				<img src = "<%=b.get_imageUrl() %>" >
			</div>
			<div id = "business-info">
				<%
				String address = b.get_location().get_displayName();
                String phone = b.get_phone();
                String categories_list = "";
				for (int i = 0; i < b.get_categories().size(); i++) {
					categories_list += b.get_categories().get(i).get_title();
					if (i < b.get_categories().size()-1) {
						categories_list += ", ";
					}
				}
				String price = b.get_price();
				float rating = b.get_rating();
				%>
				<p>Address: <%=address%></p>
				<p><%=phone%></p>
				<p>Categories: <%=categories_list%></p>
				<p>Price: <%=price%></p>
				<div id = "stars">
					<p>Rating:
					<%
						int rating_int = (int) Math.floor(rating);
						for (int i = 0; i < rating_int; i++) {
					%>
					
					<span class = "fa fa-star"></span>
					
					<% }
						if (rating - rating_int != 0.0) {
					%>
							<span class = "fa fa-star-half-full"></span>
						<% }%>	
					</p>
				</div>
			</div>
		</div>

	</body>
</html>