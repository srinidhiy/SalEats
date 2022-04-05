<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Home</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="/PA2.0/WebContent/index.css">
        <script src="https://kit.fontawesome.com/3204349982.js" crossorigin="anonymous"></script>
        
        <% // check if registered
        
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
     

        img {
            margin-top: 10px;
            max-width: 95%;
            display: block;
            margin-left: auto;
            margin-right: auto;
            border-radius: 8px;
        }

        #container {
            align-items: center;
            justify-content: center;
/*            display: flex;*/
        }

        #container select {
            margin-left: 50px;
        }

        #container input[type=text] {
            background-color: aliceblue;
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


    </style>


    <body>
        <!-- TODO -->
        <div id="navigation-bar">
            <div id="saleats">
                <a href="#">SalEats!</a>
                
                <%
                	if (registered) {
                		String formattedUsername = cookies[id].getValue().replace("+", " ");
                		out.println("\tHello, " + formattedUsername + "!");
                	}
                
                %>
                
            </div>
            

            <div id="navigation">
                <a href="#"> Home </a>
                
                 <%
                	if (registered) {
                		out.println("<a href=\"/PA2.0/LogoutDispatcher\"> Logout </a>");
                	}
                	else {
                		out.println("<a href=\"auth.jsp\"> Login / Register </a>");
                	}
                
                %>
            </div>
        </div>
		
        <br>
        
        <div id="banner">
            <img src="banner1.jpeg" alt="Banner"><br>
        </div>

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

    </body>

    </html>