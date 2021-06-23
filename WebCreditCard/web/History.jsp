<%-- 
    Document   : History
    Created on : Mar 26, 2021, 4:19:29 PM
    Author     : KBowe
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Log</title>
    </head>
    <body>
        <h1>Account Log For: ${card.accountId}</h1>
        <br>
        <c:forEach var="s" items="${card.creditHistory}" >
            <p>${s}</p>
        </c:forEach>
    </body>
</html>
