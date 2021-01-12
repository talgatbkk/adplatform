<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 10.01.21
  Time: 22:18
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
        <c:redirect url="${pageContext.request.contextPath}/signin"> </c:redirect>
    </c:if>
    <jsp:include page="/jsp/Header.jsp"/>
    <form action="${pageContext.request.contextPath}/category/post" method="post">
        <h4 class="text-center">Currently available categories</h4>
        <div class="row justify-content-center">
            <ul class="list-group">
                <c:forEach var="category" items="${requestScope.categories}">
                    <li class="list-group-item">${category.categoryName}</li>
                </c:forEach>
            </ul>
        </div>
        <br>
        <div class="row justify-content-center">
            <div class="col-auto">
                <table style="with: 50%">
                    <tr>
                        <td>New category</td>
                        <td><input type="text" name="new_category" required/></td>
                        <td><input type="submit" value="Submit"/></td>
                    </tr>
                </table>

            </div>
        </div>
    </form>
</div>
</div>
</div>

</body>
</html>