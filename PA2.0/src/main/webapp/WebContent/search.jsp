<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search</title>
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
    <%
    	
        //TODO perform search
        String search_input = (String) request.getAttribute("search-input");
    	System.out.println(search_input);
    	String category = (String) request.getAttribute("category");
    	System.out.println(category);
    	ArrayList<Business> businesses = (ArrayList<Business>) request.getAttribute("business-array");

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
        	border-top: 1px solid lightgray;
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
        
        #title {
        	padding: 30px;
        	font-size: 15px;
        }
        
        #yelp {
        	text-decoration: none;
        	color: #7F7F7F;
        	padding: 30px;
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
      		out.println("<h2>Results for " + search_input + " in " + category + "</h2>");
      	%>
      
      </div>

		<div id = "businesses">
		
		<%
		
		for (Business b : businesses) {
		
		%>
			<div id = "business">
				<div id = "business-image">
					<img src = "<%=b.get_imageUrl() %>" >
				</div>
				<div id = "business-info">
					<%
					String name = b.get_name();
					String price = b.get_price();
					int review_count = b.get_reviewCount();
					float rating = b.get_rating();
					String yelp = b.get_url();
					%>
					<a id="title" href = "/PA2.0/WebContent/details.jsp?busid=<%=b.get_id()%>"><%=name%></a>
					<p>Price: <%=price%></p>
					<p>Review Count: <%=review_count%></p>
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
					
					<br>					
					<a id = "yelp" href = "<%=yelp%>" target="_blank"> Yelp Link </a>
				</div>
			</div>
		<% } %>
	</div>
	</body>
</html>