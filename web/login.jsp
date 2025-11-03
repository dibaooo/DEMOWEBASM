<%-- 
    Document   : login
    Created on : Nov 3, 2025, 2:46:17 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    
    <body>
    <h1>Guest Login</h1>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="guestLogin"/>
        Email: <input type="email" name="email" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        <input type="submit" value="Login"/>
    </form>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
    <% } %>
    <a href="register.jsp">Register</a>
    
    <h1>Staff Login</h1>
    <<form action="MainController" method="POST">
        <input type="hidden" name="action" value="staffLogin"/>
        Username: <input type="text" name="username" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        <input type="submit" value="Login"/>
    </form>
    </body>
</html>
