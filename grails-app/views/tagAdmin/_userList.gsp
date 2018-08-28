<g:if test="${tagInstance.users}">
	<g:form method="post" >
		<input type="hidden" name="id" value="${tagInstance?.id}" />
		<input type="hidden" name="version" value="${tagInstance?.version}" />
		<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
		<div class="w-row">
			<g:each in="${tagInstance.users}" var="user" status="i">
				<div class="w-col w-col-3">
					<g:link class="w-inline-block spotlight-link-block" controller='userAdmin' action='show' id='${user.id}'>
						<img src="<g:resource file='${user.primaryImage?.smallImageSquared ?: thumbnail}' />" />
					</g:link>
					<div class="spotlight-text">
			            <g:checkBox name="users" value="${user.id}" checked="false"></g:checkBox> 
			            ${user}
		            </div>
	            </div>
			</g:each>
		</div>
		<g:actionSubmit class="w-button submit-button" action="RemoveUsers" value="Remove Selected People" />
	</g:form>
</g:if>