<%-- 
    Document   : Register
    Created on : Nov 3, 2025, 1:26:30 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Guest Registration</h1>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="registerGuest"/>
        Full Name: <input type="text" name="fullName" required/><br/>
        Phone: <input type="text" name="phone" required/><br/>
        Email: <input type="email" name="email" required/><br/>
        Address: <input type="text" name="address"/><br/>
        ID Number: <input type="text" name="idNumber"/><br/>
        Date of Birth: <input type="date" name="dateOfBirth" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        <input type="submit" value="Register"/>
    </form>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
    <% } %>
    </body>
</html>
