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
    <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js" type="text/javascript"></script>

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
    <c:set var="imagePath"  value="${initParam.imageLocation}"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>




<div class="container">
    <div class="row py-3">
        <div class="col-3 order-2" id="sticky-sidebar">
            <div class="sticky-top">
                <div class="nav flex-column">
                    <a href="${pageContext.request.contextPath}/advertisement/search?search_user_id=${requestScope.advertisement.userId}">
                        <p>All advertisements from this user</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/user/view?profile_id=${requestScope.advertisement.userId}">
                        <p>Go to user's profile</p>
                    </a>
                </div>
            </div>
        </div>
        <div class="col" id="main">
        <h1>${requestScope.advertisement.title}</h1>
            <c:if test="${requestScope.advertisement.image.path != null}">
            <figure class="align-items-center">
                <img  src="/images/${requestScope.advertisement.image.path}" class="img-fluid" alt="Ad image"  width="600"
                      height="300">
            </figure>
            </c:if>
            <p>${requestScope.location.name}</p>
            <c:if test="${requestScope.advertisement.price != 0}">
                <h3>${requestScope.advertisement.price} ${priceCurrencyLocal} </h3>
            </c:if>
            <p> ${requestScope.advertisement.description} </p>

            <h4>${requestScope.advertisement.location.name}</h4>
            <c:forEach items="${requestScope.phone_numbers}" var="phone">
                <p>${phone.phoneNumber}</p>
            </c:forEach>
            <p>Posted
            <time class="timeago" datetime="${requestScope.advertisement.postedDate.time}">${requestScope.advertisement.postedDate}</time>
            </p>
        </div>
    </div>
    <div class="ui-button align-items-center">
        <div>
            <c:if test="${requestScope.belongsToCurrentUser == true && requestScope.advertisement.image.path == null}">
                <form action="${pageContext.request.contextPath}/advertisement/upload/image?ad_id=${requestScope.advertisement.adId}" method="post" enctype="multipart/form-data" onsubmit="return validateInput()">
                    Select file to upload: <input type="file" name="file" id="uploadFile"/>
                    <br />
                    <br />
                    <input class="btn btn--stroke full-width btn-outline-primary" type="submit" value="Upload image">
                </form>
            </c:if>
        </div>
    </div>
        <div class="ui-button">
        <c:if test="${requestScope.belongsToCurrentUser == true || sessionScope.role_id == 1}">
        <form action="${pageContext.request.contextPath}/advertisement/delete" method="post">
            <input type="hidden" name="ad_id" value=${advertisement.adId}>
            <input class="btn btn--stroke full-width btn-outline-danger" type="submit" value="Delete advertisement">
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
					<a href="${pageContext.request.contextPath}/user/view?profile_id=${comment.authorId}">${comment.authorFirstName} ${comment.authorLastName}</a>
					</span>
                <span>
                    <time class="timeago" datetime="${comment.postedDate.time}">

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
    <form action="${pageContext.request.contextPath}/advertisement/view/comment/post" class="form-control-plaintext" method="post">
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
<script type="text/javascript">
    function validateInput()
    {
        if (document.getElementById('uploadFile').value != "") {
            let file = document.getElementById('uploadFile');
            if ( /\.(jpe?g|png|gif)$/i.test(file.files[0].name) === false ) {
                alert("Please select an image!");
                return false;
            }
            return true;
        } else
            alert("Please select your image");
            return false;
    };
</script>
</body>
</html>
