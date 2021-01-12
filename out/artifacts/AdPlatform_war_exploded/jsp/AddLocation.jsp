<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 10.01.21
  Time: 17:10
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
    <form action="${pageContext.request.contextPath}/location/post" method="post">
        <h4 class="text-center">Add new locations/cities</h4>
        <div class="row justify-content-center">
            <div class="col-auto">
                <table style="with: 50%">
                    <tr>
                        <td>Parent location</td>
                        <td>
                            <select  class="form-control" name="parent_id" required>
                                <option value=""></option>
                                <c:forEach items="${requestScope.locations}" var="location">
                                    <option value="${location.id}">${location.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>New location name</td>
                        <td><input type="text" name="new_location" required/></td>
                    </tr>
                </table>
                <input type="submit" value="Submit" />
            </div>
        </div>
    </form>
</div>
</div>
</div>

</body>
</html>