<%@ page import="java.util.List" %>
<%@ page import="kz.epam.tcfp.model.Advertisement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 19.12.20
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Advertisements of the customer</title>
</head>
<body>
<%  List<Advertisement> advertisements = (ArrayList)request.getAttribute("ads");
    Iterator<Advertisement> advertisementIterator = advertisements.iterator();
    while (advertisementIterator.hasNext()){
        Advertisement advertisement = advertisementIterator.next();%>
        <h4><%=advertisement.getAdId()%></h4>
        <h4><%=advertisement.getCategory()%></h4>
        <h4><%=advertisement.getDescription()%></h4>
        <h4><%=advertisement.getLocationId()%></h4>
        <h4><%=advertisement.getPostedDate()%></h4>
        <h4><%=advertisement.getTitle()%></h4>
        <h4><%=advertisement.getPrice()%></h4>
<%
}
%>

</body>
</html>
