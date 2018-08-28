<div class="w-row">
	<div class="w-col w-col-12">
		<g:if test="${file.files}">
			<div class="w-clearfix associated-stories">
				<h4 class="h4-heading-admin">Slides</h4>
                <g:render template="fileList" />
            </div>
		</g:if>
	</div>
</div>
<div class="w-form">
	<g:formRemote 
		url="[
			controller:'fileAdmin', 
			action:'fileSearch', 
			params: ['id':file.id, 'tab':'files']
		]"
		update="[success:'list1', failure:'error1']"
		name="remoteFileForm" 
		id="${file.id}"
		method="get">
		<div class="w-row search-row">
			<div class="w-col w-col-9 search-column admin">
				<g:textField class="w-input search-text-field files-field" id="search_query1" name="q" value="${params.q}"/>
             </div>
             <div class="w-col w-col-3 w-clearfix search-column admin">
             	<input class="w-button searchsubmit" type="submit" value="Search" />
             </div>
        </div>
        <div class="w-row">
			<div class="w-col w-col-2">
				<select class="w-select select-field" name="type" size="1">
					<option value="">Type</option>
					<option value="Image">Image</option>
					<option value="Text/Doc">Text/Doc</option>
					<option value="PowerPoint">PowerPoint</option>
				</select>
			</div>
			<div class="w-col w-col-2">
				<g:checkBox name="titleOnly" value="${false}"></g:checkBox>
				<label for="titleOnly">Search Titles Only</label>
			</div>
         </div>
	</g:formRemote>
	<g:form method="post" >
		<input type="hidden" name="id" value="${file.id}" />
		<input type="hidden" name="version" value="${file?.version}" />
		<div id="list1"></div>
	</g:form>
	<div id="error1"></div>
</div>