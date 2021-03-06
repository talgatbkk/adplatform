<%--
  Created by IntelliJ IDEA.
  User: tubuntu
  Date: 09.01.21
  Time: 21:14
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


<fmt:message bundle="${thisLocal}" key="input_new_password.page.title" var="pageTitleLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.label.old_pass" var="oldPasswordLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.label.new_pass" var="newPasswordlocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.label.confirm_pass" var="confirmPasswordLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.button.save" var="saveLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.button.go_back" var="goBackLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.message.match_pass" var="matchPasswordsLocal"/>
<fmt:message bundle="${thisLocal}" key="input_new_password.message.incorrect_pass" var="incorrectPasswordLocal"/>
<html>
<head>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>

    <title>${pageTitleLocal}</title>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>

<div class="container emp-profile">
    <div class="row">
        <div class="col-md-4">
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/user/view">
        <input hidden name="profile_id" value="${sessionScope.userId}">
        <input type="submit" value="${goBackLocal}" />
    </form>
    <form action="${pageContext.request.contextPath}/user/password/post" method="post" onsubmit="return passwordMatchCheck()">
        <div class="col-md-8">
            <div class="tab-content profile-tab" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <div class="row">
                        <div class="col-md-4">
                            <label>${oldPasswordLocal}</label>
                        </div>
                        <div class="col-md-4">
                            <input type="password" id="old_pass" name="old_pass" minlength="8" required>
                        </div>
                        <div class="col-md-4">
                        <c:if test="${requestScope.wrong_pass eq true}">
                                <small class="text-danger">${incorrectPasswordLocal}</small>
                        </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>${newPasswordlocal}</label>
                        </div>
                        <div class="col-md-4">
                            <input type="password" id="pass1" name="new_pass" minlength="8" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>${confirmPasswordLocal}</label>
                        </div>
                        <div class="col-md-4">
                            <input type="password" id="pass2" name="new_pass2" minlength="8" required>
                        </div>
                    </div>
                    <input type="submit" value="${saveLocal}"/>
                </div>
            </div>
        </div>
    </form>

    <script type="text/javascript">
        function passwordMatchCheck()
        {
            let isMatching = (document.getElementById('pass1').value != document.getElementById('pass2').value);
            if (isMatching) {
                alert("${matchPasswordsLocal}");
                return false;
            } else
                return true;
        };
    </script>
</div>
<jsp:include page="/jsp/StickyFooter.jsp"/>
</body>
</html>