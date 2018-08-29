<%@ page import="cev.blackFish.Help" %>

<html>

<head>
    <meta name="layout" content="main"/>
    <title>Edit Help</title>
    <script type="text/javascript" src="<g:resource dir='js' file='ckeditor/ckeditor.js'/>"></script>
</head>

<body>
<g:render template="adminInfo"/>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>Edit Menu Set</h1>
        <g:render template="messages"/>
        <div class="w-form">
            <g:form action="save" method="post">
                <g:hiddenField name="id" value="${helpInstance?.id}"/>
                <g:hiddenField name="version" value="${helpInstance?.version}"/>
                <fieldset>
                    <g:render template="form"/>
                    <g:actionSubmit class="w-button submit-button" action="update" value="Update"/>
                    <g:actionSubmit
                            class="w-button submit-button delete"
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

