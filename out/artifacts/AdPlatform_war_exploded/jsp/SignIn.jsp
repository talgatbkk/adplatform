<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 22.12.20
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="user.label.login" var="loginLocal" />
<fmt:message bundle="${thisLocal}" key="user.label.password" var="passwordLocal"/>
<fmt:message bundle="${thisLocal}" key="user.label.sign_in" var="signInLocal"/>
<fmt:message bundle="${thisLocal}" key="user.label.incorrect_auth" var="incorrectAuthLocal"/>
<div>
<head>
    <title>${signInLocal}</title>
</head>
<div>
<jsp:include page="/jsp/Header.jsp"/>
    <c:if test="${sessionScope.userId != null}">
        <c:redirect url="home"> </c:redirect>
    </c:if>

<div class="container ">
<form class="form-horizontal" action="${pageContext.request.contextPath}/login" method="post">
    <div class="row justify-content-center">
        <div class="col-auto">
    <table class="table-responsive" style="with: 50%">
        <div class="form-group">
        <tr>
            <td>${loginLocal}</td>
            <td><input type="text" name="login" required="required" /></td>
        </tr>
        </div>
        <div class="form-group">
        <tr>
            <td>${passwordLocal}</td>
            <td><input type="password" name="password" required="required" /></td>
        </tr>
        </div>
    </table>

    <div>
        <p class="error-input text-danger" id="loginError">
            <c:if test="${incorrect_auth eq true}">
                ${incorrectAuthLocal}
            </c:if>
        </p>
    </div>
    <input type="submit" value="${signInLocal}" />
    </div>
    </div>
</form>
</div>
</div>
</div>
<jsp:include page="/jsp/StickyFooter.jsp"/>
</body>
</html>
