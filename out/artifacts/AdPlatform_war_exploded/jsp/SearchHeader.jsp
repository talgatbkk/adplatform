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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="applicationResources" var="thisLocal" />

<html>
<head>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <form action="/advertisement/search" method="post">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <c:if test="${sessionScope.userId != null}">
                <li class="nav-item active">
                    <a class="nav-link" href="/home?forward_page=input_ad">Post an advertisement</a>
                </li>
            </c:if>

            <li class="nav-item">

                    <input type="hidden" name="forward_page" value="search">
                            <select name="location_item">
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
            <li class="nav-item">
                            <select name="category_item">
                                <option value="">All</option>
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
            <li class="nav-item">
                    <tr>
                    <input class="form-control mr-sm-2" type="search" name="search_input" placeholder="Search" aria-label="Search">

            </li>
            <li class="nav-item">
                <input type="submit" value="Submit" />
            </li>

        </ul>
    </div>
    </form>
</nav>

</body>
</html>
