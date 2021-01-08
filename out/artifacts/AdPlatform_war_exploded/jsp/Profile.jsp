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
<html>
<head>
    <script src="../css/bootstrap-grid.min.css"></script>
    <script src="../js/jquery-3.5.1.min.js"></script>

    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<div class="container emp-profile">
    <form method="post">
        <div class="row">
            <div class="col-md-4">
                <div class="profile-img">
                    <img src="" alt=""/>
<%--                    <div class="file btn btn-lg btn-primary">--%>
<%--                        Change Photo--%>
<%--                        <input type="file" name="file"/>--%>
<%--                    </div>--%>
                </div>
            </div>
            <div class="col-md-2">
                <form action="/login" method="post">
                <input type="submit" class="profile-edit-btn" name="btnAddMore" value="Edit Profile"/>
                </form>
            </div>
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

                                <a class="nav-link active" data-toggle="tab" href="/advertisement/search?search_user_id=${requestScope.customer.userId}">
                                    <p>${requestScope.customer.activeAds}</p>
                                </a>
                            </div>
                        </div>
                        <div>
                            <c:if test="${requestScope.customer.banned == true && requestScope.customer.userId == sessionScope.userId}">
                                        <p class="text-danger" >You are banned from posting any ads!</p>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${requestScope.customer.banned == true && sessionScope.userId == 2}">
                                <p class="text-danger" >This user is banned from posting any ads!</p>
                            </c:if>
                        </div>
                </div>
                </div>
            </div>
        <c:choose>
        <c:when test="${requestScope.customer.userId == sessionScope.userId}">
        <form action="/user/delete">
            <input type="hidden" name="del_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="Delete account" onclick="return confirm('Are you sure you want to delete?')" />
        </form>
        </c:when>
        <c:when test="${sessionScope.role_id == 1}">
        <c:choose>
        <c:when test="${requestScope.customer.banned == true}">
        <form action="/user/unban">
            <input type="hidden" name="unban_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="Unban this user" onclick="return confirm('Are you sure you want to unban this user?')" />
        </form>
        </c:when>
        <c:otherwise>
        <form action="/user/ban">
            <input type="hidden" name="ban_user_id" value="${requestScope.customer.userId}">
            <input class="btn-outline-danger" type="submit" value="Ban this user" onclick="return confirm('Are you sure you want to ban this user?')" />
        </form>
        </c:otherwise>
        </c:choose>
        </c:when>
        </c:choose>


</body>
</html>
