
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Show Permission</title>
	</head>
	<body>
		<div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
				<g:render template="adminInfo" />
			<ol class="property-list permission">
			
				<g:if test="${permissionInstance?.permissionsString}">
				<li class="fieldcontain">
					<span id="permissionsString-label" class="property-label"><g:message code="permission.permissionsString.label" default="Permissions String" /></span>
					
						<span class="property-value" aria-labelledby="permissionsString-label"><g:fieldValue bean="${permissionInstance}" field="permissionsString"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${permissionInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="permission.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="userAdmin" action="show" id="${permissionInstance?.user?.id}">${permissionInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			
			<g:form>
				<g:hiddenField name="id" value="${permissionInstance?.id}" />
				<g:link class="button small" action="edit" id="${permissionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
			</g:form>
				
		</div>
		</div>
	</body>
</html>
