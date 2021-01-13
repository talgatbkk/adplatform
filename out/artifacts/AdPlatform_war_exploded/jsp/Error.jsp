<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 03.01.21
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />
<fmt:message bundle="${thisLocal}" key="error.page.title" var="pageTitleLocal"/>
<fmt:message bundle="${thisLocal}" key="error.message.error_occured" var="errorOccuredLocal"/>
<html>
<head>
    <title>${pageTitleLocal}</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<h3 class="text-center">${errorOccuredLocal}</h3>

</body>
</html>
