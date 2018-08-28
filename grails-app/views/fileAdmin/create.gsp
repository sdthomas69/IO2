<html>

    <head>
        <meta name="layout" content="main" />
        <title>Create File</title>
    	<script type="text/javascript" src="<g:resource dir='js' file='ckeditor/ckeditor.js' />"></script>
		<script type=text/javascript>

			 function validate() {

				 var file = upload.mfile.value;
				 if (file == '') {
					 alert('You must select a file.');
					 event.returnValue = false;
				 }
			 }
		</script>     
    </head>
    
	<body>
		<g:render template="/fileAdmin/adminInfo" />
		<g:render template="/fileAdmin/messages" />
		<div class="w-section">
			<div class="w-container body-text-container single-column admin-column">
				<div class="w-form">
					<h1>Create File</h1>
					<g:uploadForm action="save" method="post" name="upload" onsubmit="validate();">
		            	<g:if test="${params.storyId}">
			    			<input type="hidden" name="storyId" value="${params.storyId}" />
						</g:if>
						<g:if test="${params.fileId}">
			    			<input type="hidden" name="fileId" value="${params.fileId}" />
						</g:if>  
						<g:if test="${params?.author?.id}">
			    			<input type="hidden" name="author.id" value="${params.author.id}" />
						</g:if>
						<g:if test="${params.helpId}">
			    			<input type="hidden" name="helpId" value="${params.helpId}" />
						</g:if>
						<g:if test="${params.portrait}">
			    			<input type="hidden" name="portrait" value="${params.portrait}" />
						</g:if>
						<fieldset>
						
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
							
							<label for="Short-Description">Short Description:</label> 
							<input
								type="text" id="shortDescription"
								class="w-input admin-text-field value ${hasErrors(bean:file,field:'shortDescription','errors')}"
								name="shortDescription" data-name="shortDescription"
								value="${fieldValue(bean:file,field:'shortDescription')}"
								placeholder="Enter your Short Description"
							>
							
							<label for="Description-Text">Description:</label>
							<textarea 
								placeholder="A required description" 
								class="ckeditor"
								id="description" 
								name="description">
								${file.description}
							</textarea>
							
							<label for="type">File Type:</label>
							<g:select 
								name="type" 
								class="w-select"
								value="${file.type}" from="${['Image', 'Text/Doc', 'HTML', 'PowerPoint', 'Quicktime', 'Keynote', 'PDF']}"
								noSelection="['':'-Select a file type-']" 
							/>
							
							<label for="tfile">File:</label>
							<input type="file" id="tfile" name="tfile" size="40"/>
							
							<input class="w-button submit-button" type="submit" value="Create" />
						</fieldset>
						
		            </g:uploadForm>
				</div>
			</div>
		</div>
    </body>
    
</html>
