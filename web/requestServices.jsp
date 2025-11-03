<%-- 
    Document   : requestServices
    Created on : Nov 3, 2025, 2:48:02 PM
    Author     : Asus
--%>

<%@page import="dto.Service"%>
<%@page import="dto.Booking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Request Additional Services</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Request Additional Services</h1>

    <% 
        ArrayList<Booking> bookings = (ArrayList<Booking>) request.getAttribute("bookings");
        ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
    %>

    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="requestServices"/>

        <!-- Dropdown Booking -->
        <label>Select Booking:</label>
        <select name="bookingID" required>
            <option value="">-- Select Booking --</option>
            <% if (bookings != null && !bookings.isEmpty()) { 
                for (Booking b : bookings) { %>
                    <option value="<%= b.getBookingID() %>">
                        Booking #<%= b.getBookingID() %> 
                        (<%= b.getCheckInDate() %> to <%= b.getCheckOutDate() %>)
                    </option>
                <% }
            } else { %>
                <option disabled>No active bookings</option>
            <% } %>
        </select>
        <br/><br/>

        <!-- Bảng Services -->
        <label>Services:</label><br/>
        <table>
            <tr>
                <th>Select</th>
                <th>Service Name</th>
                <th>Type</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <% if (services != null && !services.isEmpty()) { 
                for (Service s : services) { %>
                    <tr>
                        <td><input type="checkbox" name="serviceID" value="<%= s.getServiceID() %>"/></td>
                        <td><%= s.getServiceName() %></td>
                        <td><%= s.getServiceType() %></td>
                        <td><%= s.getPrice() %></td>
                        <td>
                            <input type="number" name="quantity" value="0" min="0" max="10" 
                                   style="width:50px" 
                                   oninput="this.form.elements['serviceID'][<%= services.indexOf(s) %>].checked = this.value > 0"/>
                        </td>
                    </tr>
                <% }
            } else { %>
                <tr><td colspan="5">No services available</td></tr>
            <% } %>
        </table>
        <br/>

        <input type="submit" value="Request Services"/>
    </form>

    <br/>
    <a href="guestHome.jsp">Back to Home</a>

    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
    <% } %>
</body>
</html>
