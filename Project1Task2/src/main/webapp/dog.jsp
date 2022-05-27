<%--
  Created by IntelliJ IDEA.
  User: tongmuyu
  Date: 2022/02/06
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dog Profile</title>
</head>
<body>
    <h1>Dog: <%= request.getAttribute("breed") %></h1>
    <h2>Friendly: <%=request.getAttribute("friendly")%> Stars</h2>
    <h2>Height: <%=request.getAttribute("height")%></h2>
    <h2>Weight: <%=request.getAttribute("weight")%></h2>
    <h2>Lifespan: <%=request.getAttribute("lifespan")%></h2>
    <b>Credit: https://dogtime.com/dog-breeds/profiles</b><br>
    <img src="<%=request.getAttribute("image")%>"><br>
    <b>Credit: https://dog.ceo/dog-api/</b> <br>
    <button onclick="window.location.href='./index.jsp'">Continue</button>
</body>
</html>
