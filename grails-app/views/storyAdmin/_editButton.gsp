<shiro:hasPermission permission="storyAdmin:edit:${story.id}">
	<div class="w-section admin-section">
		<div class="w-container admin-container">
			<ul class="w-list-unstyled w-clearfix admin-nav-bar-list">
			<g:if test="${story.updateMessage}">
		        <div>
		            ${story.updateMessage.encodeAsRaw()}
		        </div>
		    </g:if>
		    <g:link class="w-inline-block admin-link edit-button" action="edit" controller="storyAdmin" id="${story.id}"> 
				<img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png' />" height="35">
				<div class="admin-link-text-block">Edit</div>
			</g:link>
			</ul>
		</div>
	</div>
</shiro:hasPermission>