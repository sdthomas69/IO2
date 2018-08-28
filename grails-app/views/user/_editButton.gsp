<shiro:hasPermission permission="userAdmin:edit:${user.id}">
    <div class="buttons">
        <g:form controller="userAdmin" action="edit" method="get">
			<input type="hidden" name="id" value="${user?.id}" />
	        <g:actionSubmit class="button" value="Edit" />
	    </g:form>
	</div>
</shiro:hasPermission>