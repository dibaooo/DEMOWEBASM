<%-- 
    Document   : payment
    Created on : Nov 3, 2025, 2:49:22 PM
    Author     : Asus
--%>

<%@page import="dto.Booking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Checkout and Payment</h1>
        <% 
            ArrayList<Booking> bookings = (ArrayList<Booking>) request.getAttribute("bookings");
            if (bookings != null && !bookings.isEmpty()) {
        %>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="makePayment"/>
            <select name="bookingID" required>
                <% for (Booking b : bookings) { %>
                <option value="<%= b.getBookingID() %>">
                    Booking #<%= b.getBookingID() %> - Room <%= b.getRoomID() %> 
                    (<%= b.getCheckInDate() %> to <%= b.getCheckOutDate() %>)
                </option>
                <% } %>
            </select><br/><br/>
            Payment Method:
            <input type="radio" name="paymentMethod" value="Cash" required> Cash
            <input type="radio" name="paymentMethod" value="Credit Card"> Credit Card
            <input type="radio" name="paymentMethod" value="Online"> Online<br/><br/>
            <input type="submit" value="Checkout & Pay"/>
        </form>
        <% } else { %>
        <p>No checked-in bookings available for payment.</p>
        <% } %>
    </body>
</html>
