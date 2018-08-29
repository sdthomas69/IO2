<shiro:isLoggedIn>
    <div class="w-section admin-section">
        <div class="w-container admin-container">
            <ul class="w-list-unstyled w-clearfix admin-nav-bar-list">
                <g:if test="${tagInstance?.id}">
                    <shiro:hasPermission permission="tagAdmin:edit:${tagInstance.id}">
                        <li class="admin-nav-bar-list-item">
                            <g:link class="w-inline-block admin-link edit-button" action="edit" controller="tagAdmin"
                                    id="${tagInstance.id}">
                                <img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png'/>"
                                     height="35">

                                <div class="admin-link-text-block">Edit</div>
                            </g:link>
                        </li>
                    </shiro:hasPermission>
                </g:if>
                <shiro:hasPermission permission="tagAdmin:list">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" controller="tagAdmin" action="list">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='storylist-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">Tag List</div>
                        </g:link>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission permission="storyAdmin:create">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" action="create" controller="tagAdmin">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='addstory-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">New Tag</div>
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
                <shiro:hasRole name="Administrator">
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link" action="home" controller="admin">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='intraneticon-white.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">
                                Admin Home
                            </div>
                        </g:link>
                    </li>
                </shiro:hasRole>
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

