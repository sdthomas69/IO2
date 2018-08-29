<shiro:isLoggedIn>
    <div class="w-section admin-section">
        <div class="w-container admin-container">
            <ul class="w-list-unstyled w-clearfix admin-nav-bar-list">
                <g:if test="${siteInstance?.id}">
                    <shiro:hasPermission permission="site:edit:${siteInstance.id}">
                        <li class="admin-nav-bar-list-item">
                            <g:link class="w-inline-block admin-link edit-button" action="edit" controller="help"
                                    id="${siteInstance.id}">
                                <img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png'/>"
                                     height="35">

                                <div class="admin-link-text-block">Edit</div>
                            </g:link>
                        </li>
                    </shiro:hasPermission>
                </g:if>
                <shiro:hasPermission permission="site:index">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" controller="site" action="index">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='storylist-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">Site List</div>
                        </g:link>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission permission="site:create">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" action="create" controller="site">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='addstory-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">New Site</div>
                        </g:link>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission permission="userAdmin:login">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" action="login" controller="userAdmin">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='userhome-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">
                                <shiro:principal/> Home
                            </div>
                        </g:link>
                    </li>
                </shiro:hasPermission>
                <li class="admin-nav-bar-list-item">
                    <g:link class="w-inline-block admin-link" action="home" controller="admin">
                        <img class="icon-admin-menu" src="<g:resource dir='images' file='intraneticon-white.png'/>"
                             height="35">

                        <div class="admin-link-text-block">
                            Intranet
                        </div>
                    </g:link>
                </li>
                <li class="admin-nav-bar-list-item">
                    <g:link class="w-inline-block admin-link" action="signOut" controller="auth">
                        <img class="icon-admin-menu" src="<g:resource dir='images' file='gear.png'/>" height="35">

                        <div class="admin-link-text-block">
                            Log out
                        </div>
                    </g:link>
                </li>
            <%--<li class="admin-nav-bar-list-item">
                <a class="w-inline-block admin-link" href="#">
                    <img class="icon-admin-menu" src="<g:resource dir='images' file='intranet-white.png' />" height="35">
                    <div class="admin-link-text-block">
                        Help
                    </div>
                </a>
            </li>
            --%>
                <shiro:hasPermission permission="help:*">
                    <li class="admin-nav-bar-list-item">
                        <blackFish:getHelp
                                controller="${controllerName}"
                                action="${actionName}"
                                template="/help/button"/>
                    </li>
                </shiro:hasPermission>
            </ul>
        </div>
    </div>
</shiro:isLoggedIn>
