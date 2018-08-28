<div class="w-row">
	<div class="w-col w-col-12">
					<g:if test="${tagInstance.stories}">
                   <h4 class="h4-heading-admin">Tagged Stories</h4>
                   <g:render template="storyList" />
              </g:if>
				</div>
			</div>
		
			<div class="w-form">
				<g:formRemote 
		class="stories-form files"
		url="[
			controller:'tagAdmin', 
			action:'storySearch', 
			params: ['id':tagInstance.id, sort:'date', order:'desc', 'tab':'stories']
		]" 
		update="[success:'list', failure:'error']"
		name="remoteForm" 
		id="${tagInstance.id}"
		method="get">
		<div class="w-row search-row">
             		<div class="w-col w-col-9 search-column admin">
             			<g:textField class="w-input search-text-field files-field" id="search_query" name="q" value="${params.q}"/>
             		</div>
             		<div class="w-col w-col-3 w-clearfix search-column admin">
             			<input class="w-button searchsubmit" type="submit" value="Search" />
             		</div>
             	</div>
             	<div class="w-row">
          		<div class="w-col w-col-2">
           		<select class="w-select select-field" name="bool" size="1">
					<option value="">Options</option>
				</select>
			</div>
			<div class="w-col w-col-2">
				<select class="w-select select-field" name="category" size="1">
					<option value="">Category</option>
				</select>
			</div>
			<div class="w-col w-col-2">
				<g:checkBox name="titleOnly" value="${false}"></g:checkBox>
				<label for="titleOnly">Titles Only</label>
			</div>
		</div>
	</g:formRemote>
             <div id="spinner" class="spinner" style="display:none;">
                 <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
             </div>
	<g:form method="post">
		<input type="hidden" name="id" value="${tagInstance.id}" />
		<input type="hidden" name="version" value="${tagInstance?.version}" />
		<div id="list"></div>
    </g:form>
	<div id="error"></div>
</div>