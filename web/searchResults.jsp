<%-- 
    Document   : searchResults
    Created on : Nov 3, 2025, 2:47:08 PM
    Author     : Asus
--%>

<%@page import="dto.Room"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Available Rooms</h1>
    <%
        ArrayList<Room> rooms = (ArrayList<Room>) request.getAttribute("availableRooms");
        if (rooms != null && !rooms.isEmpty()) {
    %>
    <table border="1">
        <tr><th>Room ID</th><th>Room Number</th><th>Type ID</th><th>Status</th><th>Action</th></tr>
        <% for (Room room : rooms) { %>
        <tr>
            <td><%= room.getRoomID() %></td>
            <td><%= room.getRoomNumber() %></td>
            <td><%= room.getRoomTypeID() %></td>
            <td><%= room.getStatus() %></td>
            <td><a href="booking.jsp?roomID=<%= room.getRoomID() %>">Book</a></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No rooms available.</p>
    <% } %>
    </body>
</html>
