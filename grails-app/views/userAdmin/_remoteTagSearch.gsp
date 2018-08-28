<g:if test="${searchResult?.getTotalCount() > 0}">
    <ul class="w-list-unstyled w-clearfix tags-list">
		<g:each var="result" in="${searchResult}" status="i">
			<li class="tag-list-item">
                <div class="w-checkbox w-clearfix tag-link-checkbox">
                <g:checkBox name="tags" value="${result.id}" checked="false"></g:checkBox> 
                <label class="w-form-label" for="checkbox">
                	<g:link class="tag-heading" controller='tag' action='show' id='${result.id}'>
                		${result}
                	</g:link>
                </label>
			</li>
		</g:each>
	</ul>
	<g:actionSubmit class="w-button submit-button" action="AddTags" value="Add Selected Topics" />
	<util:remoteNonStopPageScroll 
		loadingHTML="spinner" 
		heightOffset="300" 
		action="tagSearch" 
		total="${searchResult?.getTotalCount()}" 
		update="list" 
		params="${params}" 
	/>
</g:if>
<g:else><p>There are no results for your query</p></g:else>