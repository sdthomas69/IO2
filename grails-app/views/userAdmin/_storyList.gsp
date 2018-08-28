<div class="w-row">
	<g:if test="${userInstance.stories}">
		<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
		<div class="w-row">
			<g:each in="${userInstance.stories.sort()}" var="thisStory" status="i">
				<blackFish:tagWrapper 
					tag="div" 
					className="w-row file-list-row"
					iterations="4"
					counter="${i}"
					total="${userInstance.stories.size()}">
					<div class="w-col w-col-3">
						<g:link action="show" controller="story" id="${thisStory.id}">
							<img src="<g:resource file='${thisStory?.primaryImage?.smallImageSquared ?: thumbnail}' />" />
						</g:link>
						<div class="file-list-text">
							<g:link action="show" controller="story" id="${thisStory.id}">
								${thisStory}
							</g:link>
						</div>
					</div>
				</blackFish:tagWrapper>
			</g:each>
		</div>
	</g:if>
</div>