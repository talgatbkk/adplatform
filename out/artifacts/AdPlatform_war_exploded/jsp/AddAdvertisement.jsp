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
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" var="thisLocal" />
<div>
<head>
    <title>Title</title>
</head>
</div>
<div>
    <c:if test="${sessionScope.userId == null}">
        <c:redirect url="signin"> </c:redirect>
    </c:if>
<jsp:include page="/jsp/Header.jsp"/>
<form action="/test" method="post">
    <div class="row justify-content-center">
        <div class="col-auto">
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
            <td>Location</td>
            <td>
                <select  class="form-control" name="location_item" required>
                    <option value=""></option>
                    <c:forEach items="${requestScope.locations}" var="location">
                        <option value="${location.id}">${location.name}</option>
                    </c:forEach>
                </select>
            </td>

        </tr>
        <tr>
            <td>Category</td>
            <td>
            <select  class="form-control" name="category_item" required>
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
    <input type="submit" value="Submit" />
</form>
</div>
</div>
</div>

</body>
</html>
