<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 02.01.21
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="inform_banned.page.title" var="pageTitleLocal"/>
<html>
<head>
    <title>${pageTitleLocal}</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>
    <h3 class="text-center">${pageTitleLocal}</h3>
<jsp:include page="/jsp/StickyFooter.jsp"/>
</body>
</html>
