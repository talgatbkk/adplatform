<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 24.12.20
  Time: 22:20
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
<fmt:message bundle="${thisLocal}" key="profile.label.edit_profile" var="editProfileLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.change_password" var="changePasswordLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.delete_account" var="deleteAccountLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.ban_user" var="banAccountLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.label.unban_user" var="unbanAccountLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.message.confirm_ban" var="confirmBanLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.message.confirm_unban" var="confirmUnbanLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.message.confirm_delete" var="confirmDeleteLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.message.user_banned" var="userIsBannedLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.message.you_banned" var="youAreBannedLocal"/>
<fmt:message bundle="${thisLocal}" key="profile.page.title" var="pageTitleLocal"/>
<html>
<head>
    <title>${pageTitleLocal}</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>
<div class="container emp-profile">
        <div class="row">
            <c:if test="${sessionScope.userId == requestScope.customer.userId}">
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/user/open_editing" method="post">
                <input type="submit" class="profile-edit-btn" name="btnAddMore" value="${editProfileLocal}"/>
                </form>
            </div>
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/user/password" method="post">
                    <input type="submit" class="profile-edit-btn" name="btnAddMore" value="${changePasswordLocal}"/>
                </form>
            </div>
            </c:if>
        </div>
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="row">
                            <div class="col-md-6">
                                <label>${loginLocal}</label>
                            </div>
                            <div class="col-md-6">
                                <p>${requestScope.customer.login}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>${fNameLocal}</label>
                            </div>
                            <div class="col-md-6">
                                <p>${requestScope.customer.firstName}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>${lNameLocal}</label>
                            </div>
                            <div class="col-md-6">
                                <p>${requestScope.customer.lastName}</p>
                            </div>
                        </div>
                        <c:if test="${requestScope.customer.userId == sessionScope.userId}">
                        <div class="row">
                            <div class="col-md-6">
                                <label>${emailLocal}</label>
                            </div>
                            <div class="col-md-6">
                                <p>${requestScope.customer.email}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>${phoneLocal}</label>
                            </div>
                            <div class="col-md-6">
                                <p>${requestScope.customer.phoneNumbers[0].phoneNumber}</p>
                            </div>
                        </div>
                        <div>
                            <c:if test="${requestScope.customer.phoneNumbers[1].phoneNumber != null}">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>${secondPhoneLocal}</label>
                                    </div>
                                    <div class="col-md-6">
                                        <p>${requestScope.customer.phoneNumbers[1].phoneNumber}</p>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        </c:if>
                        <div class="row">
                            <div class="col-md-6">
                                <label>${activeAdsLocal}</label>
                            </div>
                            <div class="col-md-6">

                                <a class="nav-link active" data-toggle="tab" href="${pageContext.request.contextPath}/advertisement/search?search_user_id=${requestScope.customer.userId}">
                                    <p>${requestScope.customer.activeAds}</p>
                                </a>
                            </div>
                        </div>
                        <div>
                            <c:if test="${requestScope.customer.banned == true && requestScope.customer.userId == sessionScope.userId}">
                                        <p class="text-danger" >${youAreBannedLocal}</p>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${requestScope.customer.banned == true && sessionScope.userId == 2}">
                                <p class="text-danger" >${userIsBannedLocal}</p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        <c:choose>
        <c:when test="${requestScope.customer.userId == sessionScope.userId}">
        <form action="${pageContext.request.contextPath}/user/delete">
            <input type="hidden" name="del_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="${deleteAccountLocal}" onclick="return confirm('${confirmDeleteLocal}')" />
        </form>
        </c:when>
        <c:when test="${sessionScope.role_id == 1}">
        <c:choose>
        <c:when test="${requestScope.customer.banned == true}">
        <form action="${pageContext.request.contextPath}/user/unban">
            <input type="hidden" name="unban_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="${unbanAccountLocal}" onclick="return confirm('${confirmUnbanLocal}')" />
        </form>
        </c:when>
        <c:otherwise>
        <form action="${pageContext.request.contextPath}/user/ban">
            <input type="hidden" name="ban_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="${banAccountLocal}" onclick="return confirm('${confirmBanLocal}')" />
        </form>
        </c:otherwise>
        </c:choose>
        </c:when>
        </c:choose>
</div>
</body>
</html>
