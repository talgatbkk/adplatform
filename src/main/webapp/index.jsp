<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 14.12.20
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <jsp:include page="/jsp/Header.jsp"/>
  <h1>HELLO WORLD!</h1>

  <form action="/signin" method="post">
<%--    <input type="hidden" name="forward_page" value="sign_in">--%>

    </div>
    <input type="submit" value="Sign In"  /></form>

  <form action="/signup" method="post">
<%--    <input type="hidden" name="forward_page" value="sign_up">--%>

    </div>
    <input type="submit" value="Registration"  /></form>

  </body>
</html>
