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

<fmt:message bundle="${thisLocal}" key="home.page.title" var="pageTitleLocal"/>
<fmt:message bundle="${thisLocal}" key="home.message.no_ads" var="noSuchAdsLocal"/>
<fmt:message bundle="${thisLocal}" key="home.label.currency" var="currencyLocal"/>
<fmt:message bundle="${thisLocal}" key="label.view_button" var="viewButtonLocal"/>
<fmt:message bundle="${thisLocal}" key="pagination.button.next" var="nextButtonLocal"/>
<fmt:message bundle="${thisLocal}" key="pagination.button.previous" var="previousButtonLocal"/>
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
    <title>${pageTitleLocal}</title>
</head>
<body>
<jsp:include page="/jsp/SearchHeader.jsp"/>
<br>
    <c:choose>
    <c:when test="${requestScope.advertisements.size() == 0}">
        <h3 class="text-center">${noSuchAdsLocal}</h3>
    </c:when>
    <c:otherwise>
    <c:forEach var="advertisement" items="${requestScope.advertisements}">
    <div class="card mb-4 mx-auto" style="max-width: 640px;">
        <div class="row no-gutters">
            <div class="col-md-4 my-auto" >
                <c:choose>
                    <c:when test="${advertisement.image.path != null}">
                        <div class="card">
                        <img src="images/${advertisement.image.path}" class="card-img" alt="">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <img src="../defaultImages/noimg.png" class="card-img" alt="">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${advertisement.title}</h5>
                    <c:choose>
                        <c:when test="${advertisement.description.length() > 43}">
                            <p class="card-text">${advertisement.description.substring(0,42)}...</p>
                        </c:when>
                        <c:otherwise>
                            <p class="card-text">${advertisement.description}</p>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${advertisement.price != 0}">
                        <p class="card-text">${advertisement.price} ${currencyLocal}</p>
                    </c:if>
                    <time class="timeago" datetime="${advertisement.postedDate.time}">${advertisement.postedDate}</time>
                    <p class="card-text">${advertisement.location.name}</p>
                    <form action="${pageContext.request.contextPath}/advertisement/view?ad_id=${advertisement.adId}" method="post">
                        <input class="btn btn--stroke full-width" type="submit" value="${viewButtonLocal}">
                    </form>
                </div>
            </div>
        </div>
    </div>
    </c:forEach>
    </c:otherwise>
    </c:choose>
<div class="text-center">
        <ul class="pagination justify-content-center">
            <c:if test="${requestScope.cur_page > 1}">
                <li><a class="btn btn-light" href="/home?page=${requestScope.cur_page - 1}">${previousButtonLocal}</a></li>
            </c:if>
            <c:if test="${requestScope.total_ad_count / requestScope.page_ad_count > requestScope.cur_page}">
                <li><a class="btn btn-light" href="home?page=${requestScope.cur_page + 1}">${nextButtonLocal}</a></li>
            </c:if>
        </ul>
</div>
<jsp:include page="/jsp/Footer.jsp"/>
</body>
</html>
