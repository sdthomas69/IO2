<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:if test="${params.view == 'list'}">
	<div class="datagrid">
	    <table>
	        <thead>
	            <tr>
	       	        <g:sortableColumn 
	       	        	property="title" 
	       	        	title="Title" 
	       	        	params="[
	       	        		'max':params?.max, 
	       	        		'offset':params?.offset, 
	       	        		'type':params.type, 
	       	        		'view':params.view, 
	       	        		'site':params.site
	       	        	]" 
	       	        />
	                <g:sortableColumn 
	                	property="date" 
	                	title="Date" 
	                	params="[
	                		'max':params?.max, 
	                		'offset':params?.offset, 
	                		'type':params.type, 
	                		'view':params.view, 
	                		'site':params.site
	                	]" 
	                />
	            </tr>
	        </thead>
	        <tbody>
		        <g:each in="${fileList}" status="i" var="file">
		            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		                <td>
		                	<g:link controller="file" action="title" params="['title':file.urlTitle]">
		                		${file}
		                	</g:link>
		                </td>
		                <td>
		                	<g:formatDate format="yyyy-MM-dd" date="${file.date}" />
		                </td>
		            </tr>
		        </g:each>
	        </tbody>
	    </table>
	</div>
 </g:if>
 <g:else>
    <g:each in="${fileList}" var="file" status="i">
		<blackFish:tagWrapper 
			tag="div" 
			className="w-row file-list-row"
			iterations="4"
			counter="${i}"
			total="${fileList.getTotalCount()}">
			<div class="w-col w-col-3">
				<g:link controller="file" action="show" id="${file.id}">
					<img src="<g:resource file='${file?.smallImageSquared ?: thumbnail}' />" />
				</g:link>
				<div class="file-list-text">
					<g:link controller="file" action="show" id="${file.id}">
						${file}
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
			total="${fileList.getTotalCount()}" 
		/>
	</div>
</div>