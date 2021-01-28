<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 30.12.20
  Time: 16:50
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
<fmt:message bundle="${thisLocal}" key="header.placeholder.search" var="placeholderSearchLocal"/>
<fmt:message bundle="${thisLocal}" key="header.button.find" var="findLocal"/>
<fmt:message bundle="${thisLocal}" key="header.label.all_categories" var="allCategoriesLocal"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">MyAds.kz</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <div>
        <form action="${pageContext.request.contextPath}/advertisement/search" method="post">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/user/view">${profileLocal} <span class="sr-only">(current)</span></a>
            </li>
            <c:if test="${sessionScope.role_id != 1}">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/advertisement/add">${postAdLocal}</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.role_id == 1}">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/location/add">${addLocationLocal}</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/category/add">${addCategoryLocal}</a>
                </li>
            </c:if>

            <li class="nav-item active">

                            <select class="form-control" name="location_item">
                                <c:forEach items="${requestScope.locations}" var="location">
                                    <c:choose>
                                        <c:when test="${requestScope.location_selection == location.id}">
                                            <option selected value="${location.id}">${location.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${location.id}">${location.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

            </li>
            <li class="nav-item active">
                            <select  class="form-control" name="category_item">
                                <option value="">${allCategoriesLocal}</option>
                                <c:forEach items="${requestScope.categories}" var="category">
                                    <c:choose>
                                        <c:when test="${requestScope.category_selection == category.categoryId}">
                                            <option selected value="${category.categoryId}">${category.categoryName}</option>
                                        </c:when>
                                        <c:otherwise>
                                        <option value="${category.categoryId}">${category.categoryName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
            </li>
            <li class="nav-item active">
                <c:choose>
                    <c:when test="${requestScope.prev_search_input == null}">
                    <input class="form-control" type="search" name="search_input" placeholder="${placeholderSearchLocal}" aria-label="Search">
                    </c:when>
                    <c:otherwise>
                        <input class="form-control" type="search" value="${requestScope.prev_search_input}" name="search_input" placeholder="${placeholderSearchLocal}" aria-label="Search">
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="nav-item active">
                <input class="form-control" type="submit" value="${findLocal}" />
            </li>
        </ul>
        </form>
        </div>
        <ul class="navbar-nav ml-auto">
            <div>
                <c:choose>
                    <c:when test="${sessionScope.local == 'ru'}">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/language?code=en">English</a>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.local == 'en'}">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/language?code=ru">Русский</a>
                        </li>
                    </c:when>
                </c:choose>
            </div>
            <div>
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
            </div>
        </ul>
    </div>
</nav>
</body>
</html>
