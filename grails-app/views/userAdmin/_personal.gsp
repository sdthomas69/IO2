<div class="w-form">
	<g:form method="post">
		<input type="hidden" name="id" value="${userInstance?.id}" />
        <input type="hidden" name="version" value="${userInstance?.version}" />
		<g:render template="/userAdmin/form" />
		<g:actionSubmit class="w-button submit-button" value="Update" />
		<shiro:hasRole name="Administrator">
        	<g:actionSubmit class="w-button submit-button delete" onclick="return confirm('Are you sure?');" value="Delete" />
		</shiro:hasRole>
	</g:form>
</div>