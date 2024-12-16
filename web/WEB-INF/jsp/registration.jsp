<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12/5/2024
  Time: 2:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="firstName">First name:
        <input type="text" name="firstName" id="firstName">
    </label><br>
    <label for="secondName">Second name:
        <input type="text" name="lastName" id="secondName">
    </label><br>
    <label for="login">Login:
        <input type="text" name="login" id="login">
    </label><br>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday">
    </label><br>
    <label for="email">E-mail:
        <input type="text" name="email" id="email">
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br>
   <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}">${gender}
       <br>
   </c:forEach>
    <button type="submit">Register</button>
    <c:if test="${not empty requestScope.errors}">
        <div>
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>

</form>
</body>
</html>
