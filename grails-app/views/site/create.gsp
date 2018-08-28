<html>
    
    <head>
        <meta name="layout" content="main" />
        <title>Create Help</title>
        <tm:resources jquery="false" opacity=".9"/>  
    </head>
    
    <body>
        <g:render template="adminInfo" />
        <g:render template="messages" />
        <div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
	            <h1>Create Site</h1>
	            <g:render template="messages" />
	            <div class="w-form">
		            <g:form action="save" method="post" >
		                <fieldset>
							<g:render template="form"/>
							<g:submitButton name="create" class="button" value="Create" />
						</fieldset>
		            </g:form>
	            </div>
	        </div>
	    </div>
    </body>
    
</html>
