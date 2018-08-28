<shiro:isLoggedIn>
	<div class="w-section admin-section">
		<div class="w-container admin-container">
			<ul class="w-list-unstyled w-clearfix admin-nav-bar-list">
				<g:if test="${file?.id}">
					<shiro:hasPermission permission="fileAdmin:edit:${file.id}">
						<li class="admin-nav-bar-list-item">
							<g:link class="w-inline-block admin-link edit-button" action="edit" controller="fileAdmin" id="${file.id}"> 
								<img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png' />" height="35">
								<div class="admin-link-text-block">Edit</div>
							</g:link>
						</li>
					</shiro:hasPermission>
				</g:if>
				<shiro:hasPermission permission="fileAdmin:list">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" controller="fileAdmin" action="list">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='storylist-white.png' />" height="35">
							<div class="admin-link-text-block">File List</div>
						</g:link>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission permission="fileAdmin:create">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" action="create" controller="fileAdmin">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='addstory-white.png' />" height="35">
							<div class="admin-link-text-block">New File</div>
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
				<shiro:hasRole name="Administrator">
					<li class="admin-nav-bar-list-item">
						<g:link class="w-inline-block admin-link" action="home" controller="admin">
							<img class="icon-admin-menu" src="<g:resource dir='images' file='intraneticon-white.png' />" height="35">
							<div class="admin-link-text-block">
								Admin Home
							</div>
						</g:link>
					</li>
				</shiro:hasRole>
				<li class="admin-nav-bar-list-item">
					<g:link class="w-inline-block admin-link" action="signOut" controller="auth">
						<img class="icon-admin-menu" src="<g:resource dir='images' file='gear.png' />" height="35">
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
							template="/help/button" 
						/>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</div>
</shiro:isLoggedIn>


<%--<shiro:hasAnyRole in="['User', 'Administrator']">
	<ul class="button-group">
		<shiro:hasRole name="Administrator">
			<li><a class="tiny button secondary" href="${createLinkTo(dir:'/admin')}">Admin Home</a></li>
			<li><g:link class="tiny button secondary" action="create" controller="fileAdmin">New File</g:link></li>
		</shiro:hasRole>
		<li><g:link class="tiny button secondary" controller="fileAdmin" action="list">List Files</g:link></li>
		<li><g:link class="tiny button secondary" action="login" controller="userAdmin"><shiro:principal/> Home</g:link></li>
		<li><g:link class="tiny button secondary" controller="auth" action="signOut">log out</g:link></li>
		<shiro:hasPermission permission="help:*">
			<li>
				<blackFish:getHelp 
					controller="${controllerName}" 
					action="${actionName}" 
					template="/help/button" 
				/>
			</li>
		</shiro:hasPermission>
	</ul>
</shiro:hasAnyRole>--%>