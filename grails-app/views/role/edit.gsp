<%@ page import="cev.blackFish.Role" %>

<html>
	<head>
		<meta name="layout" content="main" />
        <title>Edit Role</title>
        <tm:resources jquery="false" opacity=".9"/>
	</head>
	<body>
		
		<g:render template="adminInfo" />
        <div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
	            <h1>Edit Role</h1>
	            <g:render template="messages" />
	            <div class="w-form">
		            <g:form action="save" method="post" >
		                <g:hiddenField name="id" value="${roleInstance?.id}" />
						<g:hiddenField name="version" value="${roleInstance?.version}" />
		                <fieldset>
							<g:render template="form"/>
							<g:actionSubmit class="w-button submit-button" action="update" value="Update" />
							<g:actionSubmit 
								class="w-button submit-button delete" 
								action="delete" value="Delete" 
								formnovalidate="" 
								onclick="return confirm('Are you sure? This cannot be undone.');" 
							/>
						</fieldset>
		            </g:form>
	            </div>
	        </div>
	    </div>
	</body>
	
</html>

