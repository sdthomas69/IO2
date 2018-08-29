<%@ page import="cev.blackFish.MenuSet" %>
<html>

<head>
    <meta name="layout" content="main"/>
    <title>${menuSetInstance}</title>
</head>

<body>
<g:render template="adminInfo"/>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>${menuSetInstance}</h1>
        <g:render template="messages"/>
        <g:form>
            <g:hiddenField name="id" value="${menuSetInstance?.id}"/>
            <g:link class="w-button submit-button" action="edit" id="${menuSetInstance?.id}">
                Edit
            </g:link>
        </g:form>
    </div>
</div>
</body>

</html>
