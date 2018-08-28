<html>
	<head>
		<meta name="layout" content="main" />
		<title>Login</title>
	</head>
	
	<body>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
    	<div class="w-section">
    		<div class="w-container body-text-container primary-small contains-admin-nav-bar">
    			<h2>Login</h2>
    		 	<div class="w-form">
					<g:form action="signIn" method="post">
	                	<input type="hidden" name="targetUri" value="${targetUri}" />
	                	<div class="w-row">
		   					<div class="w-col w-col-6">
			                	<label for="name">Username:</label>
			                	<input 
			                		class="w-input admin-text-field" 
			                		name="username" 
			                		value="${username}" 
			                		type="text" 
			                		placeholder="Username"   
			                	/>
							</div>
						</div>
						<div class="w-row">
		   					<div class="w-col w-col-6">
								<label for="name">Password:</label>
								<input 
									class="w-input admin-text-field"
									name="password" 
									value="" 
									type="password" 
									placeholder="Password"   
								/>
							</div>
						</div>
						<input class="w-button submit-button" type="submit" value="Login">
					</g:form>
					<g:if test="${username}">
						<div class="w-row">
		   					<div class="w-col w-col-6">
								<label for="name">Forgot Your Password?</label>
								<p><g:link controller="user" action="resetPassword">Reset</g:link></p>
							</div>
						</div>
					</g:if>
    		 	</div>
	    	</div>
	    </div>
	</body>

</html>
