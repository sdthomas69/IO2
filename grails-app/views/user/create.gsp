<%@ page import="cev.blackFish.User" %>
<html>

<head>
    <meta name="layout" content="main"/>
    <title>Sign Up</title>
</head>

<body>
<g:render template="/userAdmin/errors"/>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>Sign Up</h1>

        <div class="w-form">
            <g:form action="save" method="post" useToken="true">
                <fieldset>
                    <g:render template="/userAdmin/form"/>
                    <g:submitButton name="create" class="w-button submit-button" value="Create My Account"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</div>
</body>

</html>
