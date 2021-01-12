<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 23.12.20
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" var="thisLocal" />

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
<c:if test="${sessionScope.userId != null}">
    <c:redirect url="${pageContext.request.contextPath}/home"> </c:redirect>
</c:if>
<h1>${titleLocal}</h1>
<form action="${pageContext.request.contextPath}/user/post" method="post" onsubmit="return passwordMatchCheck()">
    <div class="row justify-content-center">
        <div class="col-auto">
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
            <td>
            <c:if test="${requestScope.login_taken == true}">
                <small class="text-danger">Login is already registred</small>
            </c:if>
            </td>
        </tr>

        <tr>
            <td>${emailLocal}</td>
            <td><input type="email" name="email" required/></td>
            <td>
            <c:if test="${requestScope.email_taken == true}">
                <small class="text-danger">Email is already registred</small>
            </c:if>
            </td>
        </tr>
        <tr>
            <td>${phoneNumberLocal}</td>
            <td><input type="tel" name="phoneNumber" id="phone1" pattern="[+]{1}[0-9]{11,14}" required /></td>
            <td>
            <c:if test="${requestScope.phone_number_taken == true}">
                <small class="text-danger">Phone number is already registred</small>
            </c:if>
            </td>
        </tr>
        <tr>
            <td>${passwordLocal}</td>
            <td><input type="password" id="pass1" name="password" minlength="8" required/></td>
        </tr>
        <tr>
            <td>Confirm password</td>
            <td><input type="password" id="pass2" name="confirm_password" minlength="8" required/></td>
        </tr>
    </table>
    <input type="submit" value="Submit" />
        </div>
    </div>
</form>

<script type="text/javascript">
    function passwordMatchCheck()
        {
        let isMatching = (document.getElementById('pass1').value != document.getElementById('pass2').value);
        if (isMatching) {
            alert("Please enter matching passwords");
            return false;
        } else
            return true;
        };
</script>
</body>
</html>
