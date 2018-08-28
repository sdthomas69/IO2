<shiro:isLoggedIn>
	<div class="w-section admin-section">
		<div class="w-container admin-container">
			<ul class="w-list-unstyled w-clearfix admin-nav-bar-list">
				<g:if test="${helpInstance?.id}">
					<shiro:hasPermission permission="help:edit:${helpInstance.id}">
						<li class="admin-nav-bar-list-item">
							<g:link class="w-inline-block admin-link edit-button" action="edit" controller="help" id="${helpInstance.id}"> 
								<img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png' />" height="35">
								<div class="admin-link-text-block">Edit</div>
							</g:link>
						</li>
					</shiro:hasPermission>
				</g:if>
				<shiro:hasPermission permission="help:index">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" controller="help" action="index">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='storylist-white.png' />" height="35">
							<div class="admin-link-text-block">Help List</div>
						</g:link>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission permission="help:create">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" action="create" controller="help">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='addstory-white.png' />" height="35">
							<div class="admin-link-text-block">New Help</div>
						</g:link>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission permission="userAdmin:login">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" action="login" controller="userAdmin">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='userhome-white.png' />" height="35">
							<div class="admin-link-text-block">
								<shiro:principal /> Home
							</div>
						</g:link>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission permission="admin:home">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" action="home" controller="admin" >
							<img class="icon-admin-menu" src="<g:resource dir='images' file='intraneticon-white.png' />" height="35">
							<div class="admin-link-text-block">
								Admin Home
							</div>
						</g:link>
					</li>
				</shiro:hasPermission>
				<li class="admin-nav-bar-list-item">
					<g:link class="w-inline-block admin-link" action="signOut" controller="auth">
						<img class="icon-admin-menu" src="<g:resource dir='images' file='gear.png' />" height="35">
						<div class="admin-link-text-block">
							Log out
						</div>
					</g:link>
				</li>
				<shiro:hasPermission permission="help:*">
					<li class="admin-nav-bar-list-item">
						<blackFish:getHelp 
							controller="${controllerName}" 
							action="${actionName}" 
							template="/help/button" 
						/>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</div>
</shiro:isLoggedIn>
