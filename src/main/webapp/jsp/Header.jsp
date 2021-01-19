<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 25.12.20
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />
<fmt:message bundle="${thisLocal}" key="header.link.profile" var="profileLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.post_ad" var="postAdLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.add_category" var="addCategoryLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.add_location" var="addLocationLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.login" var="logInLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.sign_up" var="signUpLocal"/>
<fmt:message bundle="${thisLocal}" key="header.link.log_out" var="logOutLocal"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">MyAds.kz</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/user/view">${profileLocal} <span class="sr-only">(current)</span></a>
            </li>
            <c:if test="${sessionScope.role_id != 1}">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/advertisement/add">${postAdLocal}</a>
            </li>
            </c:if>
            <c:if test="${sessionScope.role_id eq 1}">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/location/add">${addLocationLocal}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/category/add">${addCategoryLocal}</a>
            </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ml-auto">
            <div>
            <c:choose>
                <c:when test="${sessionScope.local eq 'ru'}">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/language?pick=en">English</a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.local eq 'en'}">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/language?pick=ru">Русский</a>
                    </li>
                </c:when>
            </c:choose>
            </div>
                <c:choose>
                <c:when test="${sessionScope.userId == null}">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/signin">${logInLocal}</a>
                </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/signup">${signUpLocal}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">${logOutLocal}</a>
                    </li>
                </c:otherwise>
                </c:choose>
            </ul>
    </div>
</nav>
</body>
</html>
