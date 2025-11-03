<%-- 
    Document   : paymentSuccess
    Created on : Nov 3, 2025, 3:12:10 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Success</title>
    </head>
<body>
    <h1 style="color:green">Payment Successful!</h1>
    <p>Invoice ID: <%= request.getAttribute("invoiceID") %></p>
    <p>Total Amount: <%= String.format("%.2f", request.getAttribute("grandTotal")) %> VND</p>
    <a href="MainController?action=staffLogin">Back to Staff Home</a>
</body>
</html>
