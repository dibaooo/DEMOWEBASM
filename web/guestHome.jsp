<%-- 
    Document   : guestHome
    Created on : Nov 3, 2025, 3:03:14 PM
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
    <h1>Welcome, <%= ((dto.Guest) session.getAttribute("guest")).getFullName() %></h1>
    <a href="searchRooms.jsp">Search Rooms</a><br/>
    <a href="requestServices.jsp">Request Services</a><br/>
    <a href="payment.jsp">Checkout & Pay</a><br/>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="logout"/>
        <input type="submit" value="Logout"/>
    </form>
    </body>
</html>
