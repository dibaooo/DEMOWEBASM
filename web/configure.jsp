<%-- 
    Document   : configure
    Created on : Nov 3, 2025, 2:53:29 PM
    Author     : Asus
--%>

<%@page import="dto.Service"%>
<%@page import="dao.ConfigDAO"%>
<%@page import="dto.Config"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
<body>
    <h1>System Configuration</h1>
  
    <h2>Configs</h2>
    <%
        ArrayList<Config> configs = (ArrayList<Config>) request.getAttribute("configs");
        if (configs == null) {
            ConfigDAO dao = new ConfigDAO();
            configs = dao.getAllConfigs();
            request.setAttribute("configs", configs);
        }
        if (configs != null) {
    %>
    <table border="1">
        <tr><th>Key</th><th>Value</th><th>Action</th></tr>
        <% for (Config c : configs) { %>
        <tr>
            <td><%= c.getConfigKey() %></td>
            <td><%= c.getConfigValue() %></td>
            <td>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="configureSystem"/>
                    <input type="hidden" name="configAction" value="update"/>
                    <input type="hidden" name="configKey" value="<%= c.getConfigKey() %>"/>
                    New Value: <input type="text" name="newValue" value="<%= c.getConfigValue() %>"/>
                    <input type="submit" value="Update"/>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>
    <!-- Services -->
    <h2>Manage Services</h2>
    <%
        ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
        if (services != null) {
    %>
    <table border="1">
        <tr><th>ID</th><th>Name</th><th>Type</th><th>Price</th></tr>
        <% for (Service s : services) { %>
        <tr>
            <td><%= s.getServiceID() %></td>
            <td><%= s.getServiceName() %></td>
            <td><%= s.getServiceType() %></td>
            <td><%= s.getPrice() %></td>
        </tr>
        <% } %>
    </table>
    <% } %>
    <h3>Add New Service</h3>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="configureSystem"/>
        <input type="hidden" name="configAction" value="addService"/>
        Name: <input type="text" name="serviceName" required/><br/>
        Type: <input type="text" name="serviceType" required/><br/>
        Price: <input type="number" name="price" step="0.01" required/><br/>
        <input type="submit" value="Add"/>
    </form>
</body>
</html>
