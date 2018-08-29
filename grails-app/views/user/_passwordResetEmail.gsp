<%@ page contentType="text/html" %>
<html>
<head>
    <title>Reset Password</title>
</head>

<body>
<h3 class="aligncenter title">Confirm Password Reset for ${userInstance}</h3>
<g:link
        controller="auth"
        action="confirm"
        absolute="true"
        params="['nonce': userInstance?.nonce, 'updatePassword': 'true']">
    Confirm Reset
</g:link>
</body>
</html>
