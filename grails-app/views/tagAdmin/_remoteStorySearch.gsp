<g:if test="${searchResult?.getTotalCount() > 0}">
	<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
	<div class="w-row">
		<div class="w-col w-col-9 w-clearfix description-text-column">
			<g:each var="result" in="${searchResult}" status="i">
				<blackFish:tagWrapper 
					tag="div" 
					className="w-row file-list-row"
					iterations="4"
					counter="${i}"
					total="${searchResult?.getTotalCount()}">
					<div class="w-col w-col-3 spacing">
						<a href="<blackFish:setLink object='${result}' name='story' />">
							<img src="<g:resource file='${result?.primaryImage?.smallImageSquared ?: thumbnail}' />" />
						</a>
						<div class="file-list-text">
							<g:checkBox name="stories" value="${result.id}" checked="false"></g:checkBox> ${result.title}
						</div>
					</div>
				</blackFish:tagWrapper>
			</g:each>
		</div>
		<div class="w-col w-col-3 sidebar-right totop">
			<g:actionSubmit class="w-button submit-button" action="AddStories" value="Add Selected Stories" />
		</div>
	</div>
	<util:remoteNonStopPageScroll 
		loadingHTML="spinner" 
		heightOffset="150" 
		action="storySearch" 
		total="${searchResult?.getTotalCount()}" 
		update="list" 
		params="${params}" 
	/>
</g:if>
<g:else><p>There are no results for your query</p></g:else>
