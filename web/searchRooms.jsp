<%-- 
    Document   : searchRooms
    Created on : Nov 3, 2025, 2:46:51 PM
    Author     : Asus
--%>

<%@page import="dto.RoomType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.RoomDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Search Available Rooms</title>
        <script>
            function validateDates() {
                const checkIn = new Date(document.getElementsByName("checkInDate")[0].value);
                const checkOut = new Date(document.getElementsByName("checkOutDate")[0].value);
                const today = new Date();
                today.setHours(0, 0, 0, 0);

                if (checkIn < today) {
                    alert("Check-in date cannot be in the past.");
                    return false;
                }
                if (checkOut <= checkIn) {
                    alert("Check-out must be after check-in.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
<body>
<h1>Search Available Rooms</h1>
    <form action="MainController" method="POST" onsubmit="return validateDates();">
        <input type="hidden" name="action" value="searchRooms"/>
        Check-in Date: <input type="date" name="checkInDate" required/><br/><br/>
        Check-out Date: <input type="date" name="checkOutDate" required/><br/><br/>
        Room Type: 
        <select name="roomTypeID" required>
            <% 
                RoomDAO dao = new RoomDAO();
                ArrayList<RoomType> types = dao.getAllRoomTypes();
                if (types != null) {
                    for (RoomType t : types) {
            %>
            <option value="<%= t.getRoomTypeID() %>"><%= t.getTypeName() %> (Capacity: <%= t.getCapacity() %>, Price: <%= t.getPricePerNight() %>)</option>
            <% 
                    }
                }
            %>
        </select><br/><br/>
        <input type="submit" value="Search"/>
    </form>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
    <% } %>
</body>
</html>