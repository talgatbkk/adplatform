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
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="add_advertisement.button.submit" var="submitLocal" />
<fmt:message bundle="${thisLocal}" key="add_advertisement.label.price" var="priceLocal"/>
<fmt:message bundle="${thisLocal}" key="add_advertisement.label.category" var="categoryLocal"/>
<fmt:message bundle="${thisLocal}" key="add_advertisement.label.location" var="locationLocal"/>
<fmt:message bundle="${thisLocal}" key="add_advertisement.label.description" var="descriptionLocal"/>
<fmt:message bundle="${thisLocal}" key="add_advertisement.label.title" var="titleLocal"/>
<fmt:message bundle="${thisLocal}" key="add_advertisement.page.title" var="pageTitleLocal"/>

<div>
<head>
    <title>${pageTitleLocal}</title>
</head>
</div>
<div>
    <c:if test="${sessionScope.userId == null}">
        <c:redirect url="${pageContext.request.contextPath}/signin"> </c:redirect>
    </c:if>
<jsp:include page="/jsp/Header.jsp"/>
<form action="${pageContext.request.contextPath}/advertisement/post" method="post">
    <div class="row justify-content-center">
        <div class="col-auto">
    <table style="with: 50%">
        <tr>
            <h3 class="text-center">${pageTitleLocal}</h3>
        </tr>
        <tr>
            <td>${titleLocal}</td>
            <td><input type="text" name="ad_title" required/></td>
        </tr>
        <tr>
            <td>${descriptionLocal}</td>
            <td><input type="text" name="ad_description" required/></td>
        </tr>
        <tr>
            <td>${locationLocal}</td>
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
            <td>${categoryLocal}</td>
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
            <td>${priceLocal}</td>
            <td><input type="number" name="price" required/></td>
        </tr>
        </table>
    <input type="submit" value="${submitLocal}" />
        </div>
    </div>
</form>
</div>
</div>
</div>

</body>
</html>
