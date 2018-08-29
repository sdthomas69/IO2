<%@ page import="cev.blackFish.Site" %>
<html>

<head>
    <meta name="layout" content="main"/>
    <title>Edit Site</title>
    <tm:resources jquery="false" opacity=".9"/>
</head>

<body>
<g:render template="adminInfo"/>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>Edit Site</h1>
        <g:render template="messages"/>
        <div class="w-form">
            <g:form action="save" method="post">
                <g:hiddenField name="id" value="${siteInstance?.id}"/>
                <g:hiddenField name="version" value="${siteInstance?.version}"/>
                <fieldset>
                    <g:render template="form"/>
                    <g:actionSubmit class="button small success" action="update" value="Update"/>
                    <g:actionSubmit
                            class="button small alert"
                            action="delete" value="Delete"
                            formnovalidate=""
                            onclick="return confirm('Are you sure? This cannot be undone.');"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</div>
</body>

</html>