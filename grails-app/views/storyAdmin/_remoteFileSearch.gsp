
<g:if test="${searchResult?.getTotalCount() > 0}">
	<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
	<div class="w-row">
		<div class="w-col w-col-9 w-clearfix description-text-column">
			<g:each in="${searchResult}" var="result" status="i">
				<blackFish:tagWrapper 
					tag="div" 
					className="w-row file-list-row"
					iterations="4"
					counter="${i}"
					total="${searchResult.getTotalCount()}">
					<div class="w-col w-col-3 spacing">
						<g:link controller='file' action='show' id='${result.id}'>
							<img src="<g:resource file='${result?.smallImageSquared ?: thumbnail}' />" />
						</g:link>
						<div class="file-list-text">
							<g:checkBox name="files" value="${result.id}" checked="false"></g:checkBox>
							<g:link action="show" controller="file" id="${result.id}">
								 ${result.title}
							</g:link>
						</div>
					</div>
				</blackFish:tagWrapper>
			</g:each>
			<%--<util:remoteNonStopPageScroll 
				loadingHTML="spinner" 
				heightOffset="250" 
				action="fileSearch" 
				total="${searchResult?.getTotalCount()}" 
				update="list" 
				params="${params}" 
			/>--%>
		</div>
		<div class="w-col w-col-3 sidebar-right totop">
			<g:actionSubmit class="w-button submit-button" action="AddFiles" value="Add Selected Files" />
		</div>
	</div>
	<div class="w-col w-col-12">
		<div class="paginateButtons">                           
		    <div class="paginateButtons">                           
			    <util:remotePaginate 
			    	action="fileSearch" 
			    	total="${searchResult?.getTotalCount()}" 
			    	update="list" 
			    	params="${params}" 
			    />
			</div>
		</div>
	</div>
</g:if>
<g:else><p>There are no results for your query</p></g:else>

