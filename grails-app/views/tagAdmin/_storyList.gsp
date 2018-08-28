<g:form method="post" >
	<input type="hidden" name="id" value="${tagInstance?.id}" />
	<input type="hidden" name="version" value="${tagInstance?.version}" />
	<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
	<div class="w-row">
		<g:each in="${tagInstance.stories}" var="thisStory" status="i">
			<div class="w-col w-col-3">
				<g:link class="w-inline-block spotlight-link-block" controller='story' action='show' id='${thisStory.id}'>
					<img src="<g:resource file='${thisStory.primaryImage?.smallImageSquared ?: thumbnail}' />" />
				</g:link>
				<div class="spotlight-text">
		            <g:checkBox name="stories" value="${thisStory.id}" checked="false"></g:checkBox> 
		            ${thisStory}
	            </div>
            </div>
		</g:each>
	</div>
	<div class="w-row">
		<g:actionSubmit class="w-button submit-button" action="RemoveStories" value="Remove Selected Stories" />
	</div>
</g:form>