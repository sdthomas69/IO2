<g:if test="${userInstance.tags}">
	<g:form method="post" >
		<input type="hidden" name="id" value="${userInstance?.id}" />
		<input type="hidden" name="version" value="${userInstance?.version}" />
		<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
		<ul class="w-list-unstyled w-clearfix tags-list">
			<g:each in="${userInstance.tags}" var="tag" status="i">
				<li class="tag-list-item">
	                <div class="w-checkbox w-clearfix tag-link-checkbox">
		                <g:checkBox name="tags" value="${tag.id}" checked="false"></g:checkBox> 
		                <label class="w-form-label" for="checkbox">
		                	<g:link class="tag-heading" controller='tag' action='show' id='${tag.id}'>
		                		${tag}
		                	</g:link>
		                </label>
	                </div>
				</li>
			</g:each>
		</ul>
		<g:actionSubmit class="w-button submit-button" action="RemoveTags" value="Remove Selected Tags" />
	</g:form>
</g:if>
<div class="w-row">
	<div class="w-col w-col-8 w-clearfix">
		<label for="Search-Tags">or search existing tags:</label>
		<g:formRemote 
			url='[controller:"userAdmin", action:"tagSearch", id:userInstance.id]' 
			update="[success:'list', failure:'error']"
			name="remoteForm" 
			id="${userInstance.id}"
			method="get"
			>
            <g:textField class="w-input admin-text-field tag-search" id="search_query" name="q" value="${params?.q?.encodeAsHTML()}"/>
          	<input class="w-button button tag-search" type="submit" value="Search" />
		</g:formRemote>
		<div id="spinner" class="spinner1" style="display:none;">
			<img src="<g:resource file='images/spinner.gif' />" alt="Spinner" />
		</div>
		<div id="list"></div>
		<div id="error"></div>
	</div>
</div>