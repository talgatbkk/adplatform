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

<fmt:message bundle="${thisLocal}" key="user.label.login" var="loginLocal" />
<fmt:message bundle="${thisLocal}" key="user.label.password" var="passwordLocal"/>
<fmt:message bundle="${thisLocal}" key="user.label.sign_in" var="signInLocal"/>
<fmt:message bundle="${thisLocal}" key="user.label.incorrect_auth" var="incorrectAuthLocal"/>
<fmt:message bundle="${thisLocal}" key="view_advertisement.label.price_currency" var="priceCurrencyLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.message.select_image" var="selectImageLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.placeholder.your_comment" var="yourCommentLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.button.delete_ad" var="deleteAdLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.button.upload_image" var="uploadImageLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.label.comments" var="commentsLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.message.only_images" var="onlyImagesLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.label.no_comments" var="noCommentsLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.message.select_image_to_upload" var="selectImageToUploadLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.label.posted" var="postedLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.link.all_ads_user" var="allAdsOfThisUserLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.link.go_to_profile" var="goUserProfileLocal" />
<fmt:message bundle="${thisLocal}" key="view_advertisement.button.post" var="postButtontLocal"/>

<html>
<head>
    <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js" type="text/javascript"></script>

    <c:if test="${sessionScope.local eq 'en'}">
        <script src="../js/jquery.timeago.js" type="text/javascript"></script>
    </c:if>
    <c:if test="${sessionScope.local eq 'ru'}">
        <script src="../js/jquery.timeago.js" type="text/javascript"></script>
        <script src="../js/jquery.timeago.ru.js" type="text/javascript"></script>
    </c:if>

    <script type="text/javascript">
        jQuery(document).ready(function() {
            $("time.timeago").timeago();
        });
    </script>
    <c:set var="imagePath"  value="${initParam.imageLocation}"/>
    <title>MyAds.kz ${requestScope.advertisement.title}</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>
<div class="container">
    <div class="row py-3">
        <div class="col-3 order-2" id="sticky-sidebar">
            <div class="sticky-top">
                <div class="nav flex-column">
                    <a href="${pageContext.request.contextPath}/advertisement/search?search_user_id=${requestScope.advertisement.userId}">
                        <p>${allAdsOfThisUserLocal}</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/user/view?profile_id=${requestScope.advertisement.userId}">
                        <p>${goUserProfileLocal}</p>
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
            <p>${postedLocal}
            <time class="timeago" datetime="${requestScope.advertisement.postedDate.time}">${requestScope.advertisement.postedDate}</time>
            </p>
        </div>
    </div>
    <div class="ui-button align-items-center">
        <div>
            <c:if test="${requestScope.belongsToCurrentUser eq true && requestScope.advertisement.image.path == null}">
                <form action="${pageContext.request.contextPath}/advertisement/upload/image?ad_id=${requestScope.advertisement.adId}" method="post" enctype="multipart/form-data" onsubmit="return validateInput()">
                    ${selectImageToUploadLocal} <input type="file" name="file" id="uploadFile"/>
                    <br />
                    <br />
                    <input class="btn btn--stroke full-width btn-outline-primary" type="submit" value="${uploadImageLocal}">
                </form>
            </c:if>
        </div>
    </div>
        <div class="ui-button">
        <c:if test="${requestScope.belongsToCurrentUser eq true || sessionScope.role_id eq 1}">
        <form action="${pageContext.request.contextPath}/advertisement/delete" method="post">
            <input type="hidden" name="ad_id" value=${advertisement.adId}>
            <input class="btn btn--stroke full-width btn-outline-danger" type="submit" value="${deleteAdLocal}">
        </form>
        </c:if>
        </div>
</div>
<div class="container align-content-center">
    <div class="be-comment-block">
        <c:choose>
        <c:when test="${requestScope.comments.size() != 0}">
        <p>${commentsLocal} (${requestScope.comments.size()})</p>
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
    <c:when test="${requestScope.comments == null || requestScope.comments.size() == 0}">
        <p class="comments-title">${noCommentsLocal}</p>
    </c:when>
    </c:choose>
    <c:if test="${sessionScope.userId != null}">
    <form action="${pageContext.request.contextPath}/advertisement/view/comment/post" class="form-control-plaintext" method="post">
        <input type="hidden" name="ad_id" value="${requestScope.advertisement.adId}">
        <div class="row">
            <div>
                <div >
                    <textarea name="comment_content" required="" placeholder="${yourCommentLocal}"></textarea>
                </div>
            </div>
        </div>
        <input class="btn" type="submit" value="${postButtontLocal}" />
    </form>
    </c:if>

    </div>
    <script type="text/javascript">
        function validateInput()
        {
            if (document.getElementById('uploadFile').value != "") {
                let file = document.getElementById('uploadFile');
                if ( /\.(jpe?g|png|gif)$/i.test(file.files[0].name) === false ) {
                    alert("${onlyImagesLocal}");
                    return false;
                }
                return true;
            } else
                alert("${selectImageLocal}");
                return false;
        };
    </script>
    <c:if test="${requestScope.advertisement.image.path eq null}">
        <jsp:include page="/jsp/StickyFooter.jsp"/>
    </c:if>
</body>
</html>
