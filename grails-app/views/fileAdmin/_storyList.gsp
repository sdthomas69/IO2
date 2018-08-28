<g:form method="post" >
	<input type="hidden" name="id" value="${file?.id}" />
	<input type="hidden" name="version" value="${file?.version}" />
	<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
		<g:each in="${file.stories}" var="thisStory" status="i">
			<g:link class="w-inline-block grid-item" controller='story' action='show' id='${thisStory.id}'>
				<img src="<g:resource file='${thisStory.primaryImage?.smallImage ?: thumbnail}' />" />
			</g:link><br />
            <g:checkBox name="stories" value="${thisStory.id}" checked="false"></g:checkBox> 
            <g:link controller='story' action='show' id='${thisStory.id}'>${thisStory}</g:link>
		</g:each>
	<g:actionSubmit class="w-button submit-button" action="RemoveStories" value="Remove Selected Stories" />
</g:form>