<div class="w-row">
	<div class="w-form">
		<g:form method="post">
			<input type="hidden" name="id" value="${tagInstance.id}" />
			<input type="hidden" name="version" value="${tagInstance?.version}" />
			<div class="w-row">
				<div class="w-col w-col-6">
					<label for="name">Title:</label> 
					<input 
						type="text" id="title"
						class="w-input admin-text-field value ${hasErrors(bean:tagInstance,field:'title','errors')}"
						name="title" data-name="title" value="${tagInstance?.title}"
					>
				</div>
			</div>
			<g:actionSubmit class="w-button submit-button" action="Update" value="Update" />
			<g:actionSubmit 
				class="w-button submit-button delete"
				onclick="return confirm('Are you sure? This cannot be undone.');"
				value="Delete" 
			/>
		</g:form>
	</div>
</div>