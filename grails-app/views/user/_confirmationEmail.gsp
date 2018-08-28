<%@ page contentType="text/html"%>
<html>
	<head>
		<title>Confirmation Email</title>
	</head>
	<body>
		<h3 class="aligncenter title">Confirmation for ${userInstance}</h3>
		<g:link controller="auth"
			action="confirm"
			absolute="true"
			params="['nonce': userInstance?.nonce]">
			Confirm
		</g:link>
	</body>
</html>
