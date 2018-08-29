<%@ page import="cev.blackFish.Role" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Show Role</title>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        %{--<g:render template="adminInfo" />--}%
        <h1>${roleInstance.name}</h1>
        <g:render template="messages"/>
        <ol class="property-list role">

            <g:if test="${roleInstance?.name}">
                <li class="fieldcontain">
                    <span id="name-label" class="property-label"><g:message code="role.name.label"
                                                                            default="Name"/></span>

                    <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${roleInstance}"
                                                                                            field="name"/></span>

                </li>
            </g:if>

            <g:if test="${roleInstance?.users}">
                <li class="fieldcontain">
                    <span id="users-label" class="property-label"><g:message code="role.users.label"
                                                                             default="Users"/></span>

                    <g:each in="${roleInstance.users}" var="u">
                        <span class="property-value" aria-labelledby="users-label"><g:link controller="user"
                                                                                           action="show"
                                                                                           id="${u.id}">${u?.encodeAsHTML()}</g:link></span>
                    </g:each>

                </li>
            </g:if>

        </ol>
        <g:form>
            <g:hiddenField name="id" value="${roleInstance?.id}"/>
            <g:link class="button small" action="edit" id="${roleInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
        </g:form>
    </div>
</div>
</body>
</html>
