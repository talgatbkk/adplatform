<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 22.12.20
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<form action="/login" method="post">
    <input type="hidden" name="forward_page" value="sign_in">
    <table style="with: 50%">

        <tr>
            <td>Login</td>
            <td><input type="text" name="login" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <div>
        <p class="error-input" id="loginError">
            <c:if test="${incorrect_auth == true}">
                ${incorrect_auth}
            </c:if>
        </p>
    </div>
    <input type="submit" value="Login" /></form>
</body>
</html>
