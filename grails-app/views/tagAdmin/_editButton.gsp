<shiro:hasPermission permission="tagAdmin:edit:${tagInstance.id}">
    <g:link class="w-inline-block admin-link edit-button" action="edit" controller="tagAdmin" id="${tagInstance.id}"> 
		<img class="icon-admin-menu" src="<g:resource dir='images' file='editwrench.png' />" height="35">
		<div class="admin-link-text-block">Edit</div>
	</g:link>
</shiro:hasPermission>