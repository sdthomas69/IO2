<%@ page import="cev.blackFish.User" %>

<html>
	<head>
		<meta name="layout" content="main">
		<title>${userInstance}</title>
	</head>
	
	<body>
		<div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
				<g:render template="adminInfo" />
				<h1>${userInstance}</h1>
				<g:render template="messages" />
				<g:form>
					<g:hiddenField name="id" value="${userInstance?.id}" />
					<g:link class="w-button submit-button" action="edit" id="${userInstance?.id}">Edit</g:link>
				</g:form>
			</div>
		</div>
	</body>
	
</html>
