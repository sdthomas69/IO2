<%@ page contentType="text/html"%>
<html>
	<head>
		<title>Confirmation Email</title>
	</head>
	<body>
		<h3 class="aligncenter title">Confirmation for ${userInstance}</h3>
		<g:form controller="auth" 
			action="confirm" 
			absolute="true"
			useToken="true"
			method="POST" 
			class="form-stacked">
			<input type="hidden" name="nonce" value="${userInstance?.nonce}" />
			<div class="actions">
	            <input class="button" type="submit" value="Confirm">
	        </div>
		</g:form>
	</body>
</html>
