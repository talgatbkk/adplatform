<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 22.12.20
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="applicationResources" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="local.label.login" var="loginLocal" />
<fmt:message bundle="${thisLocal}" key="local.label.password" var="passwordLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.signin" var="signInLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.incorrectAuth" var="incorrectAuthLocal"/>
<html>
<head>
    <title>${signInLocal}</title>
</head>
<body>
<form action="/login" method="post">
    <input type="hidden" name="forward_page" value="sign_in">
    <table style="with: 50%">

        <tr>
            <td>${loginLocal}</td>
            <td><input type="text" name="login" required="required" /></td>
        </tr>
        <tr>
            <td>${passwordLocal}</td>
            <td><input type="password" name="password" required="required" /></td>
        </tr>
    </table>
    <div>
        <p class="error-input" id="loginError">
            <c:if test="${incorrect_auth == true}">
                ${incorrectAuthLocal}
            </c:if>
        </p>
    </div>
    <input type="submit" value="${signInLocal}" /></form>
</body>
</html>
