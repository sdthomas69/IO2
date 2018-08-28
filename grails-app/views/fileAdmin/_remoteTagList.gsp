<g:if test="${availableTags.getTotalCount() > 0}">
	<div class="thumbnailContainer">
		<div class="spacer">&nbsp;</div>
		<g:each in="${availableTags}" var="tag" status="i">	
			<div class="float">
				<g:link controller='tag' action='show' id='${tag.id}'>
					<p class="imagecaption">${tag}<br />
						<g:link params='["id":file.id, "tagId":tag.id]' action='addTag'>(+ Add)</g:link>
					</p>
				</g:link>
			</div>
			<g:if test="${i % 3 == 2}">
				<div class="middleSpacer"></div>
			</g:if>
		</g:each>
		<div class="spacer">&nbsp;</div>
	</div> 
	<div class="paginateButtons">                           
		<util:remotePaginate action="listTags" total="${availableTags.getTotalCount()}" update="list" />
	</div>
</g:if>
