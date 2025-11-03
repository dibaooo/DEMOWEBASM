<%-- 
    Document   : error
    Created on : Nov 3, 2025, 3:12:29 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
<body>
    <h1 style="color:red">Error Occurred</h1>
    <p><%= request.getAttribute("error") != null ? request.getAttribute("error") : "Unknown error" %></p>
    <a href="javascript:history.back()">Go Back</a> | 
    <a href="guestHome.jsp">Home</a>
</body>
</html>
