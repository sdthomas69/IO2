<html>

<head>
    <meta name="layout" content="main"/>
    <title>Create Role</title>
</head>

<body>
<g:render template="adminInfo"/>
<g:render template="messages"/>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>Create Role</h1>
        <g:render template="messages"/>
        <div class="w-form">
            <g:form action="save" method="post">
                <fieldset>
                    <g:render template="form"/>
                    <g:submitButton name="create" class="w-button submit-button" value="Create"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</div>
</body>

</html>