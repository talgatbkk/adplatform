<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 24.12.20
  Time: 23:41
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
    <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>

    <title>Title</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<div class="container emp-profile">
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
        </div>
    <form action="${pageContext.request.contextPath}/user/view">
        <input hidden name="profile_id" value="${requestScope.customer.userId}">
        <input type="submit" value="Go back" />
    </form>
    <form action="${pageContext.request.contextPath}/user/post_edit" method="post" onsubmit="return fieldsNotMatchCheck()">

        <div class="col-md-8">
            <div class="tab-content profile-tab" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <div class="row">
                        <div class="col-md-4">
                            <label>${fNameLocal}</label>
                        </div>
                        <div class="col-md-4">
                            <p>${requestScope.customer.firstName}</p>
                        </div>
                        <div class="col-md-4">
                            <input type="text" id="new_fname" name="first_name" placeholder="New first name">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>${lNameLocal}</label>
                        </div>
                        <div class="col-md-4">
                            <p>${requestScope.customer.lastName}</p>
                        </div>
                        <div class="col-md-4">
                            <input type="text" id="new_lname" name="last_name" placeholder="New last name">
                        </div>
                    </div>
                        <div class="row">
                            <div class="col-md-4">
                                <label>${emailLocal}</label>
                            </div>
                            <div class="col-md-4">
                                <p>${requestScope.customer.email}</p>
                            </div>
                            <div class="col-md-4">
                                <input type="email" id="new_email" name="email" placeholder="New email">
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-md-8">
                        </div>
                        <c:if test="${requestScope.email_taken == true}">
                            <div class="col-md-4">
                                <small class="text-danger">Email is already registred</small>
                            </div>
                        </c:if>
                    </div>
                    <input type="submit" value="Save"/>
                </div>
            </div>
        </div>
        </form>

        <script type="text/javascript">
            function fieldsNotMatchCheck()
            { if (document.getElementById("new_fname").value
                || document.getElementById("new_lname").value
                || document.getElementById("new_email").value) {
                return true;
            } else {
                alert("Please change at least one field")
                return false;
            }
            };
        </script>

</body>
</html>