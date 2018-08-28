<g:form method="post">
	<input type="hidden" name="id" value="${file?.id}" />
	<input type="hidden" name="version" value="${file?.version}" />
	<div class="w-row">
		<div class="w-col w-col-6">
			<label for="name">Short Title:</label> 
			<input type="text" id="title"
				class="w-input admin-text-field value ${hasErrors(bean:file,field:'title','errors')}"
				name="title" data-name="title" required="required"
				value="${fieldValue(bean:file,field:'title')}"
				placeholder="A required short title for your file"
			>
			<label for="Menu-Title">Long Title:</label> 
			<input type="text"
				id="pageTitle"
				class="w-input admin-text-field value ${hasErrors(bean:file,field:'pageTitle','errors')}"
				name="pageTitle" data-name="pageTitle"
				placeholder="Optional long title for your page"
				value="${fieldValue(bean:file,field:'pageTitle')}"
			>
		</div>
		<div class="w-col w-col-6">
			<label for="URL-Title">URL Title:</label> 
			<input type="text"
				id="urlTitle" data-name="urlTitle"
				class="w-input admin-text-field value ${hasErrors(bean:file,field:'urlTitle','errors')}"
				name="urlTitle" readonly="readonly"
				placeholder="Automatically Generated"
				value="${fieldValue(bean:file,field:'urlTitle')}"
			>
			<label for="Short-Description">Short Description:</label> 
			<input
				type="text" id="shortDescription"
				class="w-input admin-text-field value ${hasErrors(bean:file,field:'shortDescription','errors')}"
				name="shortDescription" data-name="shortDescription"
				value="${fieldValue(bean:file,field:'shortDescription')}"
				placeholder="Enter your Short Description"
			>
		</div>
	</div>
	<label for="Description-Text">Description:</label>
	<textarea 
		placeholder="A required description" 
		class="ckeditor"
		id="description" 
		name="description">
		${file.description}
	</textarea>
	<label for="Meta-Keywords">Meta Keywords</label>
	<input type="text" 
		id="metaKeywords"
		class="w-input admin-text-field value ${hasErrors(bean:file,field:'metaKeywords','errors')}"
		name="metaKeywords" data-name="metaKeywords"
		value="${fieldValue(bean:file,field:'metaKeywords')}"
		placeholder="Optional keywords"
	>
	<label for="Meta-Description">Meta Description</label>
	<input 
		type="text" 
		id="metaDescription"
		class="w-input admin-text-field value ${hasErrors(file:story,field:'metaDescription','errors')}"
		name="metaDescription" data-name="metaDescription"
		value="${fieldValue(bean:file,field:'metaDescription')}"
		placeholder="Optional description"
	>
	<g:actionSubmit class="w-button submit-button" action="Update" value="Update" />
	<g:actionSubmit 
		class="w-button submit-button delete"
		onclick="return confirm('Are you sure? This cannot be undone.');"
		value="Delete" 
	/>
</g:form>