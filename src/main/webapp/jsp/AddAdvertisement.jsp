<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 27.12.20
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.applicationResources}" />
<fmt:setBundle basename="applicationResources" var="thisLocal" />
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>
<form action="/test" method="post">
    <input type="hidden" name="forward_page" value="post_ad">
    <table style="with: 50%">
        <tr>
            <td>Title</td>
            <td><input type="text" name="ad_title" required/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><input type="text" name="ad_description" required/></td>
        </tr>
        <tr>
            <td>
                <select name="location_item" required>
                    <option value=""></option>
                    <c:forEach items="${requestScope.locations}" var="location">
                        <option value="${location.id}">${location.name}</option>
                    </c:forEach>
                </select>
            </td>

        </tr>
        <tr>
            <td>
            <select name="category_item" required>
                <option value=""></option>
                <c:forEach items="${requestScope.categories}" var="category">
                <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
            </td>

        </tr>
        <tr>
            <td>Price</td>
            <td><input type="number" name="price" required/></td>
        </tr>
        </table>
    <input type="submit" value="Submit" /></form>

</body>
</html>
