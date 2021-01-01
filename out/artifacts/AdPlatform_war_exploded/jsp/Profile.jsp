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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<div class="container emp-profile">
    <form method="post">
        <div class="row">
            <div class="col-md-4">
                <div class="profile-img">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS52y5aInsxSm31CvHOFHWujqUx_wWTS9iM6s7BAm21oEN_RiGoog" alt=""/>
                    <div class="file btn btn-lg btn-primary">
                        Change Photo
                        <input type="file" name="file"/>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="profile-head">
                    <h5>
                        Kshiti Ghelani
                    </h5>
                    <h6>
                        Web Developer and Designer
                    </h6>
                    <p class="proile-rating">RANKINGS : <span>8/10</span></p>
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">About</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Timeline</a>
                        </li>
                    </ul>
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

                                <a class="nav-link active" data-toggle="tab" href="/home?forward_page=search&search_user_id=${requestScope.customer.userId}">
                                    <p>${requestScope.customer.activeAds}</p>
                                </a>
                            </div>
                        </div>
                </div>
                </div>
            </div>
        <form action="/advertisement/search" method="post">
            <input type="hidden" name="forward_page" value="get_ads">
                <input type="submit" value="Home"  />
        </form>
</body>
</html>
