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
<fmt:message bundle="${thisLocal}" key="edit_profile.placeholder.new_fname" var="newFNameLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.placeholder.new_lname" var="newLNameLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.placeholder.new_email" var="newEmailLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.button.save" var="saveLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.message.change_field" var="changeFieldLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.message.email_taken" var="emailTakenLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.button.go_back" var="goBackLocal"/>
<fmt:message bundle="${thisLocal}" key="edit_profile.page.title" var="pageTitleLocal"/>
<html>
<head>
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
        <input hidden name="profile_id" value="${requestScope.customer.userId}">
        <input type="submit" value="${goBackLocal}" />
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
                            <input type="text" id="new_fname" name="first_name" placeholder="${newFNameLocal}">
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
                            <input type="text" id="new_lname" name="last_name" placeholder="${newLNameLocal}">
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
                                <input type="email" id="new_email" name="email" placeholder="${newEmailLocal}">
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-md-8">
                        </div>
                        <c:if test="${requestScope.email_taken == true}">
                            <div class="col-md-4">
                                <small class="text-danger">${emailTakenLocal}</small>
                            </div>
                        </c:if>
                    </div>
                    <input type="submit" value="${saveLocal}"/>
                </div>
            </div>
        </div>
        </form>
</div>
        <script type="text/javascript">
            function fieldsNotMatchCheck()
            { if (document.getElementById("new_fname").value
                || document.getElementById("new_lname").value
                || document.getElementById("new_email").value) {
                return true;
            } else {
                alert("${changeFieldLocal}")
                return false;
            }
            };
        </script>

</body>
</html>