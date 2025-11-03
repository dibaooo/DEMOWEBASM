<%-- 
    Document   : manageStaff
    Created on : Nov 3, 2025, 2:51:36 PM
    Author     : Asus
--%>

<%@page import="dto.Staff"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
<body>
    <h1>Manage Staff Accounts</h1>
    <!-- Search form -->
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="manageStaff"/>
        <input type="hidden" name="staffAction" value="list"/>
        Search by Name: <input type="text" name="searchName"/>
        <input type="submit" value="Search"/>
    </form>
    <%
        ArrayList<Staff> staffs = (ArrayList<Staff>) request.getAttribute("staffs");
        if (staffs != null) {
    %>
    <table border="1">
        <tr><th>ID</th><th>Name</th><th>Role</th><th>Username</th><th>Phone</th><th>Email</th><th>Status</th><th>Actions</th></tr>
        <% for (Staff s : staffs) { %>
        <tr>
            <td><%= s.getStaffid() %></td>
            <td><%= s.getFullname() %></td>
            <td><%= s.getRole() %></td>
            <td><%= s.getUsername() %></td>
            <td><%= s.getPhone() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getStatus() == 1 ? "Active" : "Inactive" %></td>
            <td>
                <!-- Update form inline -->
                <form action="MainController" method="POST" style="display:inline">
                    <input type="hidden" name="action" value="manageStaff"/>
                    <input type="hidden" name="staffAction" value="update"/>
                    <input type="hidden" name="staffID" value="<%= s.getStaffid() %>"/>
                    Name: <input type="text" name="fullName" value="<%= s.getFullname() %>" required/><br/>
                    Role: <select name="role">
                        <option value="Receptionist" <%= "Receptionist".equals(s.getRole()) ? "selected" : "" %>>Receptionist</option>
                        <option value="Manager" <%= "Manager".equals(s.getRole()) ? "selected" : "" %>>Manager</option>
                        <option value="Housekeeping" <%= "Housekeeping".equals(s.getRole()) ? "selected" : "" %>>Housekeeping</option>
                        <option value="ServiceStaff" <%= "ServiceStaff".equals(s.getRole()) ? "selected" : "" %>>ServiceStaff</option>
                        <option value="Admin" <%= "Admin".equals(s.getRole()) ? "selected" : "" %>>Admin</option>
                    </select><br/>
                    Password: <input type="password" name="password" value="<%= s.getPassword() %>"/><br/> <!-- For demo; in production, handle separately -->
                    Phone: <input type="text" name="phone" value="<%= s.getPhone() %>"/><br/>
                    <input type="submit" value="Update"/>
                </form>
                <!-- Toggle status form -->
                <form action="MainController" method="POST" style="display:inline">
                    <input type="hidden" name="action" value="manageStaff"/>
                    <input type="hidden" name="staffAction" value="toggleStatus"/>
                    <input type="hidden" name="staffID" value="<%= s.getStaffid() %>"/>
                    <input type="hidden" name="newStatus" value="<%= s.getStatus() == 1 ? 0 : 1 %>"/>
                    <input type="submit" value="<%= s.getStatus() == 1 ? "Deactivate" : "Activate" %>"/>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>
    <!-- Add new staff form -->
    <h2>Add New Staff</h2>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="manageStaff"/>
        <input type="hidden" name="staffAction" value="add"/>
        Full Name: <input type="text" name="fullName" required/><br/>
        Role: <select name="role">
            <option value="Receptionist">Receptionist</option>
            <option value="Manager">Manager</option>
            <option value="Housekeeping">Housekeeping</option>
            <option value="ServiceStaff">ServiceStaff</option>
            <option value="Admin">Admin</option>
        </select><br/>
        Username: <input type="text" name="username" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        Phone: <input type="text" name="phone"/><br/>
        Email: <input type="email" name="email"/><br/>
        <input type="submit" value="Add"/>
    </form>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
    <% } %>
</body>
</html>