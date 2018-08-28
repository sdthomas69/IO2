<g:form method="post" controller="permission">
	<input type="hidden" name="id" value="${params?.id}" />
	<input type="hidden" name="permissionId" value="${permission?.id}" />
	<g:textField name="permissionsString" required="" value="${permission.permissionsString}"/>
	<div class="buttons">
       <span class="button"><g:actionSubmit class="save" action="updateUserPermissions" value="Update Permission" /></span>
   	</div>
</g:form>