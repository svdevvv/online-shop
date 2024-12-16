<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12/8/2024
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="/login" method="post">
    <fieldset style="width:300px">
        <label for="login">Username:
            <input type="text" name="login" id="login" value="${param.email}"${param.login}" required" />
        </label><br>
        <label for="password">Password:
            <input type="password" name="password" id="password">
        </label>
    </fieldset>
    <button type="submit">Login</button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${param.error != null}">
        <div style="color: red">
            <span>Email or password is not correct!</span>
        </div>
    </c:if>

</form>
</body>
</html>
