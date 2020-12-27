<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 26.12.20
  Time: 00:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="applicationResources" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="view_ad.label.price_currency" var="priceCurrencyLocal" />

<html>
<head>

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<figure>
    <img src="img.jpg" alt="my img"/>
    <figcaption> Your text </figcaption>
</figure>

<h3>${requestScope.advertisement.title}</h3>
<p>${requestScope.location.name}</p>
<c:if test="${requestScope.advertisement.price == 0}">
    <h3>${requestScope.advertisement.price} ${priceCurrencyLocal} </h3>
</c:if>
<p> ${requestScope.advertisement.description} </p>

<h4>${requestScope.advertisement.location.name}</h4>


<div class="container">
    <div class="be-comment-block">
        <c:choose>
        <c:when test="${requestScope.comments != null}">
        <h1 class="comments-title">Comments (${requestScope.comments.size()})</h1>
        <c:forEach var="comment" items="${requestScope.comments}">
        <div class="be-comment">
            <div class="be-comment-content">
				<span class="be-comment-name">
					<a href="blog-detail-2.html">${comment.authorFirstName} ${comment.authorLastName}</a>
					</span>
                <span class="be-comment-time">
					<i class="fa fa-clock-o"></i>
                    ${comment.postedDate}
				</span>
                <p class="be-comment-text">
                    ${comment.content}
                </p>
            </div>
        </div>
        </c:forEach>
    </div>

    </c:when>
    <c:when test="${requestScope.comments == null}">
        <h1 class="comments-title">No comments</h1>

    </c:when>
    </c:choose>
    <form action="/test" method="post">
        <input type="hidden" name="forward_page" value="post_comment">
        <input type="hidden" name="ad_id" value="${requestScope.advertisement.adId}">
        <div class="row">
            <div>
                <div >
                    <textarea name="comment_content" required="" placeholder="Your comment"></textarea>
                </div>
            </div>
        </div>
        <input type="submit" value="Post" />
    </form>


</div>

</body>
</html>
