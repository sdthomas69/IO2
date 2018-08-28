<%@ page contentType="text/html"%>
<html>
	<head>
		<title>UWPCC Directory Invitation</title>
	</head>
	<body>
		${eText}
		<g:link controller="user"
			action="create"
			absolute="true"
			params="['nonce':nonce]">
			Sign Up
		</g:link>
	</body>
</html>
