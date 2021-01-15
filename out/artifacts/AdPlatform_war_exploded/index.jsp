<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 14.12.20
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <jsp:include page="/jsp/Header.jsp"/>
<%--  <c:redirect url="${pageContext.request.contextPath}/home"> </c:redirect>--%>
  <h1>HELLO WORLD!</h1>

  <form action="/signin" method="post">
    </div>
    <input type="submit" value="Sign In"  /></form>

  <form action="/signup" method="post">
    </div>
    <input type="submit" value="Registration"  /></form>

  </body>
</html>
