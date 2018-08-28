<g:if test="${file.tags}">
	<div class="thumbnailContainer">
		<div class="spacer">&nbsp;</div>
		<g:each in="${file.tags}" var="tag" status="i">	
			<div class="float">
				<g:link controller='tag' action='show' id='${tag.id}'>
					<p class="imagecaption">${tag}<br />
						<g:link params='["id":file.id, "tagId":tag.id]' action='removeTag'>(Remove)</g:link>
					</p>
				</g:link>
			</div>
			<g:if test="${i % 4 == 3}">
				<div class="middleSpacer"></div>
			</g:if>
		</g:each>
		<div class="spacer">&nbsp;</div>
	</div> 
</g:if>