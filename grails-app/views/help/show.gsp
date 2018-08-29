<%@ page import="cev.blackFish.Help" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Show Help</title>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h1>${helpInstance}</h1>
        <ol class="property-list help">

            <g:if test="${helpInstance?.title}">
                <li class="fieldcontain">
                    <span id="title-label" class="property-label"><g:message code="help.title.label"
                                                                             default="Controller"/></span>

                    <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${helpInstance}"
                                                                                             field="title"/></span>

                </li>
            </g:if>

            <g:if test="${helpInstance?.selectedController}">
                <li class="fieldcontain">
                    <span id="selectedController-label" class="property-label"><g:message
                            code="help.selectedController.label" default="Controller"/></span>

                    <span class="property-value" aria-labelledby="selectedController-label"><g:fieldValue
                            bean="${helpInstance}" field="selectedController"/></span>

                </li>
            </g:if>

            <g:if test="${helpInstance?.activity}">
                <li class="fieldcontain">
                    <span id="activity-label" class="property-label"><g:message code="help.activity.label"
                                                                                default="Activity"/></span>

                    <span class="property-value" aria-labelledby="activity-label"><g:fieldValue bean="${helpInstance}"
                                                                                                field="activity"/></span>

                </li>
            </g:if>

        </ol>
        <g:form url="[resource: helpInstance, action: 'delete']" method="DELETE">
            <fieldset class="buttons">
                <g:link class="edit" action="edit" resource="${helpInstance}"><g:message
                        code="default.button.edit.label" default="Edit"/></g:link>
                <g:actionSubmit class="w-button submit-button delete" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
