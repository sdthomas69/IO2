<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<div class="w-row">
	<g:each in="${storyList}" var="story" status="i">
		<blackFish:tagWrapper
				tag="div"
				className="w-row file-list-row"
				iterations="4"
				counter="${i}"
				total="${storyList.getTotalCount()}">
			<div class="w-col w-col-3">
				<a href="<blackFish:setLink object='${story}' name='story' />">
					<img src="<g:resource file='${story?.primaryImage?.smallImageSquared ?: thumbnail}' />" />
				</a>
				<div class="file-list-text">
					<a href="<blackFish:setLink object='${story}' name='story' />">
						${story.pageTitle}
					</a>
				</div>
			</div>
		</blackFish:tagWrapper>
	</g:each>
	<util:remotePageScroll
			action="remoteStoryList"
			total="${storyList.getTotalCount()}"
			update="storyList"
			title="More"
			params="${params}"
	/>
</div>

