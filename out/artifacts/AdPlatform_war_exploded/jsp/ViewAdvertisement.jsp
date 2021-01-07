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
<fmt:setBundle basename="language" var="thisLocal" />

<fmt:message bundle="${thisLocal}" key="view_ad.label.price_currency" var="priceCurrencyLocal" />

<html>
<head>
    <script src="../js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="../js/jquery.timeago.js" type="text/javascript"></script>
    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<%--<figure>--%>
<%--    <img src="img.jpg" alt="my img"/>--%>
<%--    <figcaption> Your text </figcaption>--%>
<%--</figure>--%>


<div class="container">
    <div class="row py-3">
        <div class="col-3 order-2" id="sticky-sidebar">
            <div class="sticky-top">
                <div class="nav flex-column">
                    <a href="/advertisement/search?search_user_id=${requestScope.advertisement.userId}">
                        <p>All advertisements from this user</p>
                    </a>

                    <a href="/user/view?profile_id=${requestScope.advertisement.userId}">
                        <p>Go to user's profile</p>
                    </a>
                </div>
            </div>
        </div>
        <div class="col" id="main">
        <h1>${requestScope.advertisement.title}</h1>
            <p>${requestScope.location.name}</p>
            <c:if test="${requestScope.advertisement.price != 0}">
                <h3>${requestScope.advertisement.price} ${priceCurrencyLocal} </h3>
            </c:if>
            <p> ${requestScope.advertisement.description} </p>

            <h4>${requestScope.advertisement.location.name}</h4>
            <c:forEach items="${requestScope.phone_numbers}" var="phone">
                <p>${phone.phoneNumber}</p>
            </c:forEach>
        </div>
    </div>
        <div class="ui-button">
        <c:if test="${requestScope.belongsToCurrentUser == true || sessionScope.role_id == 1}">
        <form action="/advertisement/delete" method="post">
            <input type="hidden" name="ad_id" value=${advertisement.adId}>
            <input class="btn btn--stroke full-width btn-outline-danger" type="submit" value="Delete">
        </form>
        </c:if>
        </div>
</div>
<div class="container align-content-center">
    <div class="be-comment-block">
        <c:choose>
        <c:when test="${requestScope.comments != null}">
        <h4>Comments (${requestScope.comments.size()})</h4>
        <c:forEach var="comment" items="${requestScope.comments}">
        <div class="be-comment">
            <div class="be-comment-content">
				<span class="be-comment-name">
					<a href="/user/view?profile_id=${comment.authorId}">${comment.authorFirstName} ${comment.authorLastName}</a>
					</span>
                <span>
                    <time class="" datetime="${advertisement.postedDate}">
                    ${comment.postedDate.timeInRussian}
                    </time>
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
    <c:if test="${sessionScope.userId != null}">
    <form action="/advertisement/view/comment/post" class="form-control-plaintext" method="post">
        <input type="hidden" name="ad_id" value="${requestScope.advertisement.adId}">
        <div class="row">
            <div>
                <div >
                    <textarea name="comment_content" required="" placeholder="Your comment"></textarea>
                </div>
            </div>
        </div>
        <input class="btn" type="submit" value="Post" />
    </form>
    </c:if>

</div>

</body>
</html>
