<html>
    <head>
        <meta name="layout" content="main" />
        <title>Change Icon</title>
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
		            <h1>Change Icon</h1>
		            <g:uploadForm action="customIcon" method="post" name="upload" onsubmit="validate();">  
						<input type="hidden" name="id" value="${params?.id}" />
						<input type="hidden" name="version" value="${file?.version}" />
						<input type="hidden" name="type" value="Image" />
						<fieldset>
							<label for="mfile">File:</label>
							<input type="file" id="mfile" name="mfile" size="40"/>
						</fieldset>
		                <input class="w-button submit-button" type="submit" value="Upload" />
		            </g:uploadForm>
		        </div>
		     </div>
	     </div>
    </body>
</html>
