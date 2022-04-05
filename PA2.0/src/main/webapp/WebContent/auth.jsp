<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="1019043537941-60ro9ph8faincodktuvk5v21tti1d9ev.apps.googleusercontent.com"
          name="google-signin-client_id">
    <title>Login / Register</title>
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <script src="https://kit.fontawesome.com/9b2ed648bc.js" crossorigin="anonymous"></script>
    <script async defer src="https://apis.google.com/js/platform.js"></script>
    <link href="/PA2.0/WebContent/index.css" rel="stylesheet">
<%--     <link rel=stylesheet type="/text/css" href="<%= request.getContextPath() %>index.css">
 --%>    
    <link href="https://fonts.googleapis.com/css?family=Roboto"
          rel="stylesheet" type="text/css">
    <script src="https://apis.google.com/js/api:client.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    

    <title> Login/Register </title>

    <style>
        #container {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #container button {
            color: white;
            border-radius: 4px;
            width: 100%;
            height: 30px;
            font-weight: 300;
            border: none;

        }


        #container input[type=text] {
            width: 100%;
            border: 1px solid gainsboro;
            border-radius: 2px;
            padding: 5px;
            margin-top: 5px;
            margin-bottom: 15px;
        }

        #container button i {
            padding-right: 5px;


        }

        #login-button {
            background-color: crimson;
        }

        #login-google {
            background-color: dodgerblue;
        }
        
        #register-button {
            background-color: crimson;
        }

        #login, #register {
            width: 30%;
            margin: 25px;
        }
        
        #invalid-login {
        	background-color: #FFB6C1;
        	text-align: center;
        	padding-top: 20px;
        	padding-bottom: 20px;
        	margin-top: 15px;    

        }
        
        #invalid-register {
        	color: red;
        }
       
	    

    </style>

</head>
<body>
		<% 
			 String er = (String) request.getAttribute("error");
			 if ((request.getAttribute("error") != null)) {
				 out.print("<p id=\"invalid-login\">Invalid email or password. Or, bad Google login. Please try again.</p>");	 
			 }
		%>
	
	
    <div id="navigation-bar">
            <div id="saleats">
                <a href="/PA2.0/WebContent/index.jsp">SalEats!</a>
            </div>

            <div id="navigation">
                <a href="/PA2.0/WebContent/index.jsp"> Home </a>
                <a href="#"> Login / Register </a>
            </div>
    </div>


    <div id = "container">
        <div id = "login">
            <h1>Login</h1>

            <form action = "/PA2.0/LoginDispatcher" method = "GET" id = "login-form">
                <label for = "login-email"> Email </label><br>
                <input type = "text" name = "login-email" id = "login-email"><br><br>
                <label for = "login-password"> Password </label><br>
                <input type = "text" name = "login-password" id = "login-password"><br>

                <button type = "submit" id = "login-button" name = "login-button"><i class="fas fa-right-to-bracket"></i> Sign In</button><br>

<!--                 <button type = "button" id = "login-google" name = "login-google"><i class='fa fa-google'></i>Sign In with Google</button>
 -->                <!-- <div class="g-signin2" data-onsuccess="onSignIn" data-onfailure="onFailure"></div> -->
                <div id="my-signin2"></div>
                <script>
                function onSuccess(googleUser) {
                	  var profile = googleUser.getBasicProfile();
                	  console.log('Name: ' + profile.getName());
                	  console.log('Email: ' + profile.getEmail());
                	  // redirect to the google dispatcher
                	  var url = "http://localhost:8080/PA2.0/GoogleDispatcher?google-email="
                			  + encodeURIComponent(profile.getEmail()) + "&google-name="
                			  + encodeURIComponent(profile.getName());
                	  var auth2 = gapi.auth2.getAuthInstance();
                	  auth2.disconnect();
                	  window.location.replace(url);
                	}
                function onFailure(res) {
					console.log('Fail: ',res); // This is null if the 'email' scope is not present.
				}
                function renderButton() {
                    gapi.signin2.render('my-signin2', {
                      'scope': 'profile email',
                      'width': 390,
                      'height': 30,
                      'longtitle': true,
                      'theme': 'dark',
                      'onsuccess': onSuccess,
                      'onfailure': onFailure
                    });
                }
                </script>
                <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>  
                
            </form>

        </div>

        <div id = "register">
            <h1>Register</h1>

            <form action = "/PA2.0/RegisterDispatcher" method = "GET" id = "register-form">
                <label for = "register-email"> Email </label><br>
                <input type = "text" name = "register-email" id = "register-email" ><br>

                <label for = "register-name"> Name </label><br>
                <input type = "text" name = "register-name" id = "register-name"><br>

                <label for = "register-password"> Password </label><br>
                <input type = "text" name = "register-password" id = "register-password" ><br>

                <label for = "confirm-password"> Confirm Password </label><br>
                <input type = "text" name = "confirm-password" id = "confirm-password" ><br>

                <input type = "checkbox" id = "terms" name = "terms" value = "terms" required>
                <label for = "terms"> I have read and agree to all terms and conditions of SalEats. </label><br><br>

				<%
					if (request.getAttribute("blank") != null) {
						out.println("<p id = \"invalid-register\"> Some fields are left blank. </p>");
					}
					/* if (request.getAttribute("terms-error") != null) {
						out.println("<p id = \"invalid-register\"> Please accept terms. </p>");
					} */
					if (request.getAttribute("passwords-error") != null) {
						out.println("<p id = \"invalid-register\"> Passwords do not match. </p>");
					}
					if (request.getAttribute("email-format-error") != null) {
						out.println("<p id = \"invalid-register\"> Email is incorrectly formatted. </p>");
					}
					if (request.getAttribute("duplicate-email") != null) {
						out.println("<p id = \"invalid-register\"> Email is already registered. </p>");
					}
					if (request.getAttribute("name-format-error") != null) {
						out.println("<p id = \"invalid-register\"> Name is incorrectly formatted. </p>");
					}
				
				%>

                <button type = "submit" id = "register-button" name = "register-button"><i class='fa fa-user-plus'></i> Create Account </button><br>
            </form>
			
        </div>
    </div>

</body>
</html>