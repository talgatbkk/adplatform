<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 19.12.20
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="language" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="profile.label.login" var="loginLocal" />
<fmt:message bundle="${thisLocal}" key="profile.label.email" var="emailLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.first_name" var="fNameLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.last_name" var="lNameLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.phone_number" var="phoneLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.second_phone_number" var="secondPhoneLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.active_ads" var="activeAdsLocal"/>
<fmt:message bundle="${thisLocal}" key="label.view_button" var="viewButtonLocal"/>

<html>
<head>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <script src="../js/jquery-3.5.1.min.js" type="text/javascript"></script>

    <c:if test="${sessionScope.local == 'en'}">
    <script src="../js/jquery.timeago.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${sessionScope.local == 'ru'}">
    <script src="../js/jquery.timeago.js" type="text/javascript"></script>
    <script src="../js/jquery.timeago.ru.js" type="text/javascript"></script>
    </c:if>

    <script type="text/javascript">
        jQuery(document).ready(function() {
            $("time.timeago").timeago();
        });
    </script>

    <title>Advertisements of the customer</title>
</head>
<body>
<jsp:include page="/jsp/SearchHeader.jsp"/>
<br>
    <c:choose>
    <c:when test="${requestScope.advertisements.size() == 0}">

        <h3 class="text-center">No advertisements with such parameters</h3>

    </c:when>
    <c:otherwise>
    <c:forEach var="advertisement" items="${requestScope.advertisements}">
    <div class="card mb-3 mx-auto" style="max-width: 640px;">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="..." class="card-img" alt="...">
            </div>
            <div class="col-md-8">
                <div class="card-body">

                    <h5 class="card-title">${advertisement.title}</h5>
                    <p class="card-text">${advertisement.description}</p>
                    <c:if test="${advertisement.price != 0}">
                        <p class="card-text">${advertisement.price} tenge</p>
                    </c:if>
                    <time class="timeago" datetime="${advertisement.postedDate.time}">${advertisement.postedDate}</time>
                    <p class="card-text">${advertisement.location.name}</p>
                    <form action="/advertisement/view?ad_id=${advertisement.adId}" method="post">
                        <input class="btn btn--stroke full-width" type="submit" value="${viewButtonLocal}">
                    </form>
                </div>
            </div>
        </div>
    </div>
        <br>
    </c:forEach>
    </c:otherwise>
    </c:choose>
</body>
</html>
