<%@ page import="cev.blackFish.Site" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Show Site</title>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <g:render template="adminInfo"/>
        <h1>${siteInstance}</h1>
        <g:render template="messages"/>
        <ol class="property-list site">

            <g:if test="${siteInstance?.title}">
                <li class="fieldcontain">
                    <span id="title-label" class="property-label"><g:message code="site.title.label"
                                                                             default="Title"/></span>

                    <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${siteInstance}"
                                                                                             field="title"/></span>

                </li>
            </g:if>

            <g:if test="${siteInstance?.styleSheet}">
                <li class="fieldcontain">
                    <span id="styleSheet-label" class="property-label"><g:message code="site.styleSheet.label"
                                                                                  default="Style Sheet"/></span>

                    <span class="property-value" aria-labelledby="styleSheet-label"><g:fieldValue bean="${siteInstance}"
                                                                                                  field="styleSheet"/></span>

                </li>
            </g:if>

            <g:if test="${siteInstance?.portal}">
                <li class="fieldcontain">
                    <span id="portal-label" class="property-label"><g:message code="site.portal.label"
                                                                              default="Portal"/></span>

                    <span class="property-value" aria-labelledby="portal-label"><g:link controller="story" action="show"
                                                                                        id="${siteInstance?.portal?.id}">${siteInstance?.portal?.encodeAsHTML()}</g:link></span>

                </li>
            </g:if>

            <g:if test="${siteInstance?.bottom}">
                <li class="fieldcontain">
                    <span id="bottom-label" class="property-label"><g:message code="site.bottom.label"
                                                                              default="Bottom"/></span>

                    <span class="property-value" aria-labelledby="bottom-label"><g:link controller="menuSet"
                                                                                        action="show"
                                                                                        id="${siteInstance?.bottom?.id}">${siteInstance?.bottom?.encodeAsHTML()}</g:link></span>

                </li>
            </g:if>

            <g:if test="${siteInstance?.main}">
                <li class="fieldcontain">
                    <span id="main-label" class="property-label"><g:message code="site.main.label"
                                                                            default="Main"/></span>

                    <span class="property-value" aria-labelledby="main-label"><g:link controller="menuSet" action="show"
                                                                                      id="${siteInstance?.main?.id}">${siteInstance?.main?.encodeAsHTML()}</g:link></span>

                </li>
            </g:if>

        </ol>
        <g:form>
            <g:hiddenField name="id" value="${siteInstance?.id}"/>
            <g:link class="w-button submit-button" action="edit" id="${siteInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
        </g:form>
    </div>
</div>
</body>
</html>
