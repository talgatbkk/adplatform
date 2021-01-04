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

<html>
<head>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/home">MyAds.kz</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
<%--        <input type="hidden" name="page" value="sign_up">--%>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/user/view">Profile <span class="sr-only">(current)</span></a>
            </li>

            <li class="nav-item active">
                <a class="nav-link" href="/advertisement/add">Post an advertisement</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:choose>
            <c:when test="${sessionScope.userId == null}">
            <li class="nav-item active">
                <a class="nav-link" href="/signin">Log in</a>
            </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/signup">Sign Up</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item active">
                    <a class="nav-link" href="/user/logout">Log out</a>
                </li>
            </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

</body>
</html>
