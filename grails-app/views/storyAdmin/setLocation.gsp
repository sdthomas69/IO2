
<html>
    <head>
        <title>Set Location</title>
		<meta name="layout" content="main" />
    </head>
    
    <body>
		<g:render template="/storyAdmin/adminInfo" />
    	<g:render template="/storyAdmin/errors" />
    	<g:render template="/storyAdmin/steps" />
    	<div class="w-section">
    		<div class="w-container body-text-container primary-small contains-admin-nav-bar">
    			<h2>Location</h2>
    		 	<div class="w-form">
					<g:form method="post" >
	                	<input type="hidden" name="id" value="${story.id}" />
	                	<input type="hidden" name="version" value="${story?.version}" />
	                	<div class="w-row">
		   					<div class="w-col w-col-6">
			                	<label for="name">Latitude:</label>
								<input 
									type="text" 
									id="latitude" 
									class="w-input admin-text-field value ${hasErrors(bean:story,field:'latitude','errors')}" 
									name="latitude"
									data-name="latitude"
									value="<g:formatNumber number="${story?.latitude}" type="number" maxFractionDigits="8" />"
								>
							</div>
						</div>
						<div class="w-row">
		   					<div class="w-col w-col-6">
								<label for="name">Longitude:</label>
								<input 
									type="text" 
									id="longitude" 
									class="w-input admin-text-field value ${hasErrors(bean:story,field:'longitude','errors')}" 
									name="longitude"
									data-name="longitude"
									value="<g:formatNumber number="${story?.longitude}" type="number" maxFractionDigits="8" />"
								>
							</div>
						</div>
						<g:actionSubmit class="w-button submit-button" action="UpdateLocation" value="Update" />
					</g:form>
    		 	</div>
	    	</div>
	    </div>
	</body>
    
</html>
