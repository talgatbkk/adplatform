<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 23.12.20
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.applicationResources}" />
<fmt:setBundle basename="applicationResources" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="local.label.title" var="titleLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.first_name" var="firstNameLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.last_name" var="lastNameLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.login" var="loginLocal" />
<fmt:message bundle="${thisLocal}" key="local.label.password" var="passwordLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.email" var="emailLocal"/>
<fmt:message bundle="${thisLocal}" key="local.label.phone_number" var="phoneNumberLocal"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<h1>${titleLocal}</h1>
<form action="/test" method="post">
    <input type="hidden" name="forward_page" value="sign_up">
    <table style="with: 50%">
        <tr>
            <td>${firstNameLocal}</td>
            <td><input type="text" name="first_name" required/></td>
        </tr>
        <tr>
            <td>${lastNameLocal}</td>
            <td><input type="text" name="last_name" required/></td>
        </tr>
        <tr>
            <td>${loginLocal}</td>
            <td><input type="text" name="login" required/></td>
        </tr>
        <tr>
            <td>${passwordLocal}</td>
            <td><input type="password" name="password" minlength="8" required/></td>
        </tr>
        <tr>
            <td>${emailLocal}</td>
            <td><input type="email" name="email" required/></td>
        </tr>
        <tr>
            <td>${phoneNumberLocal}</td>
            <td><input type="tel" name="phoneNumber" pattern="[+]{1}[0-9]{11,14}" required/></td>
        </tr></table>
    <input type="submit" value="Submit" /></form>
</body>
</html>
