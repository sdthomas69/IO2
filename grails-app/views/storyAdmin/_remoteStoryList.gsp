<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:if test="${params.view == 'list'}">
	<div class="datagrid">
		<table>
			<thead>
				<tr>
					<g:sortableColumn 
						property="title" title="Title"
						params="[
							'max':params?.max, 
							'offset':params?.offset, 
							'category':params.category, 
							'view':params.view,
							'type':params.type,
							'bool':params.bool
						]" 
					/>
					<g:sortableColumn 
						property="date" title="Date"
						params="[
							'max':params?.max, 
							'offset':params?.offset, 
							'category':params.category, 
							'view':params.view,
							'type':params.type,
							'bool':params.bool
						]" 
					/>
				</tr>
			</thead>
			<tbody>
				<g:each in="${storyList}" status="i" var="story">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>
							<g:link action="show" controller="story" id="${story.id}">
								${story}
							</g:link>
						</td>
						<td>
							<g:formatDate format="yyyy-MM-dd" date="${story.date}" />
						</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</g:if>
<g:else>
	<g:each in="${storyList}" var="story" status="i">
		<blackFish:tagWrapper 
			tag="div" 
			className="w-row file-list-row"
			iterations="4"
			counter="${i}"
			total="${storyList.getTotalCount()}">
			<div class="w-col w-col-3">
				<g:link action="show" controller="story" id="${story.id}">
					<img src="<g:resource file='${story?.primaryImage?.smallImageSquared ?: thumbnail}' />" />
				</g:link>
				<div class="file-list-text">
					<g:link action="show" controller="story" id="${story.id}">
						${story}
					</g:link>
				</div>
			</div>
		</blackFish:tagWrapper>
	</g:each>
</g:else>
<div class="spacerdiv"></div>
<div class="w-row">
	<div class="w-col w-col-12">
		<g:paginate 
			action="list"
			params="${params}"
			total="${storyList.getTotalCount()}" 
		/>
	</div>
</div>
