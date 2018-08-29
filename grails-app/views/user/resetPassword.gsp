<%@ page import="cev.blackFish.User" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Reset Password</title>
</head>

<body>
<!-- Start:  Login Form-->
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h3 class="aligncenter title">Reset Password</h3>
        <g:if test="${flash.message}">
            <div data-alert class="alert-box success">${flash.message}</div>
        </g:if>
        <div class="w-form">
            <g:if test="${flash.invalidToken}">
                Don't click the button twice!
            </g:if>
            <g:form
                    controller="user"
                    action="findByEmail"
                    method="POST"
                    useToken="true">
                <div class="w-row">
                    <div class="w-col w-col-6">
                        <label for="name">Enter your email address:</label>
                        <g:field
                                type="email"
                                name="email"
                                required=""
                                class="w-input admin-text-field"
                                value=""/>
                    </div>
                </div>
                <g:submitButton name="create" class="w-button submit-button" value="Submit"/>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
