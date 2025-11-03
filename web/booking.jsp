<%-- 
    Document   : booking
    Created on : Nov 3, 2025, 2:47:40 PM
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
    <h1>Book Room</h1>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="makeBooking"/>
        <input type="hidden" name="roomID" value="<%= request.getParameter("roomID") %>"/>
        Check-in Date: <input type="date" name="checkInDate" required/><br/>
        Check-out Date: <input type="date" name="checkOutDate" required/><br/>
        <input type="submit" value="Book"/>
    </form>
    </body>
</html>
