<html>
<head>
    <title>Administration Home</title>
    <meta name="layout" content="main"/>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message">
        ${flash.message}
    </div>
</g:if>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <h2>Admin Home</h2>

        <div class="w-form">
            <div class="w-row">
                <div class="w-col w-col-6">
                    <ul>
                        <shiro:hasRole name="Administrator">
                            <li><g:link controller="storyAdmin" action="list">Stories</g:link></li>
                            <li><g:link controller="fileAdmin" action="list">Files</g:link></li>
                            <li><g:link controller="tagAdmin" action="list">Tags</g:link></li>
                            <li><g:link controller="menuSetAdmin" action="list"
                                        params="['max': 50, sort: 'title']">Menu Bars</g:link></li>
                            <li><g:link controller="userAdmin" action="list">Users</g:link></li>
                            <li><g:link controller="role">Roles</g:link></li>
                            <li><g:link controller="permission">Permissions</g:link></li>
                            <li><g:link controller="help">Help</g:link></li>
                            <li><a href="${createLinkTo(dir: '/auth/signOut')}">Log Out</a></li>
                        </shiro:hasRole>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

	
