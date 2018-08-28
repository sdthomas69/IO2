<g:form method="post" >
	<input type="hidden" name="id" value="${file?.id}" />
	<input type="hidden" name="version" value="${file?.version}" />
	<div class="w-row">
		<div class="w-col w-col-2">
			<label for="published">Published:</label>
			<div class="w-checkbox">
				<g:checkBox class="w-checkbox-input" name="published" value="${file.published}" ></g:checkBox>
				<label class="w-form-label" for="Publish">
					Publish
				</label>
			</div>
		</div>
		<div class="w-col w-col-6">
			<label for="date">Date:</label>
			<g:datePicker name="date" value="${file.date}" years="${2012..2020}"></g:datePicker>
		</div>
	</div>
	<hr />
	<div class="w-row">
		<div class="w-col w-col-6">
			<label for="author">Author:</label>
			<g:select 
				class="w-select" 
				id="Author"
				optionKey="id" 
				from="${cev.blackFish.User.list([sort:'firstName'])}" 
				name="author.id" 
				value="${file?.author?.id}"
				noSelection="['':'None']"
			/>
			<label for="category">Category:</label>
			<g:select 
				class="w-select"
				name="category"
				from="${['']}"  
				noSelection="['':'-None-']" 
			/>
		</div>
		<div class="w-col w-col-6">
			<label for="type">Type:</label>
			<g:select 
				class="w-select"
				name="type" 
				value="${file.type}" 
				from="${['Image', 'Text/Doc', 'HTML', 'KML', 'KMZ', 'EXE', 'Excel', 'PowerPoint', 'PDF', 'MP3', 'Quicktime', 'Keynote', 'Zip', 'ASX']}" 
				noSelection="['':'-Select a file type-']" 
			/>
			<label for="site">Site:</label>
		</div>
	</div>
	<g:actionSubmit 
		class="w-button submit-button" 
		action="Update" value="Update" 
	/>
	<g:actionSubmit 
		class="w-button submit-button delete" 
		onclick="return confirm('Are you sure?');" 
		action="Delete" 
		value="Delete" 
	/>
</g:form>