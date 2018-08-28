<html>
    
    <head>
        <meta name="layout" content="main" />
        <title>Create Tag</title>
        <tm:resources jquery="false" opacity=".9"/>  
    </head>
    
    <body>
        <g:render template="adminInfo" />
        <g:render template="messages" />
        <div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
	            <h1>Create a Tag</h1>
	            <g:render template="messages" />
	            <div class="w-form">
		            <g:form action="save" method="post" >
		                <g:if test="${params?.author?.id}">
			    			<input type="hidden" name="author.id" value="${params.author.id}" />
						</g:if>
						<g:if test="${params?.user?.id}">
			    			<input type="hidden" name="user.id" value="${params.user.id}" />
						</g:if>
		                <fieldset>
							<g:render template="form"/>
							<g:submitButton name="create" class="w-button submit-button" value="Create" />
						</fieldset>
		            </g:form>
	            </div>
	        </div>
	    </div>
    </body>
    
</html>
