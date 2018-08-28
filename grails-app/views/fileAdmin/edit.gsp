<html>
    
    <head>
        <meta name="layout" content="main" />
        <title>Edit File</title>
        <script type="text/javascript" src="<g:resource dir='js' file='ckeditor/ckeditor.js' />"></script>
	</head>
	
	<body>
		<g:render template="/fileAdmin/adminInfo" />
		<g:render template="/fileAdmin/messages" />
		<div class="w-section">
			<div class="w-container body-text-container single-column admin-column">
				<div class="w-form">
					<h2>
						<g:link controller="file" action="title" params="['title' : file.urlTitle]">
							${file}
						</g:link>
					</h2>
					<shiro:hasPermission permission="permission:findPermissionsByObject">
						<g:link controller="permission" action="findPermissionsByObject" params="['objectName':'fileAdmin', 'objectId': file.id]">
							Permissions
						</g:link>
					</shiro:hasPermission>
	                <div class="w-tabs admin-text-tabs" data-duration-in="300" data-duration-out="100">
		                <div class="w-tab-menu admin-tabs-menu-container">
    		 				<a class="w-tab-link w-inline-block tab-link-button w--current" data-w-tab="Tab 1">
    		 					 <div>Text</div>
    		 				</a>
    		 				<a class="w-tab-link w-inline-block tab-link-button" data-w-tab="Tab 2">
    		 					<div>Generated Images</div>
    		 				</a>
    		 				%{--<a class="w-tab-link w-inline-block tab-link-button" data-w-tab="Tab 3">
    		 					<div>Files</div>
    		 				</a>--}%
    		 				<a class="w-tab-link w-inline-block tab-link-button" data-w-tab="Tab 4">
								<div>Stories</div>
							</a>
							<a class="w-tab-link w-inline-block tab-link-button" data-w-tab="Tab 5">
								<div>Tags</div>
							</a>
							<a class="w-tab-link w-inline-block tab-link-button" data-w-tab="Tab 6">
								<div>Publish</div>
							</a>
    		 			</div>
    		 			<div class="w-tab-content admin-tabs-container">
   		 					<div class="w-tab-pane w--tab-active" data-w-tab="Tab 1">
    		 					<g:render template="/fileAdmin/tab1" />
   		 					</div>
   		 					<div class="w-tab-pane" data-w-tab="Tab 2">
  		 						<g:render template="/fileAdmin/tab2" />
   		 					</div>
   		 					<div class="w-tab-pane" data-w-tab="Tab 3">
		 						<g:link 
		 							class="w-button submit-button" 
		 							controller="fileAdmin" 
		 							params="['fileId':file?.id]" 
		 							action="create"
		 							>
									+&nbsp;Add A New File
								</g:link>
		 						%{--<g:render template="/fileAdmin/tab3" />--}%
		 					</div>
	 						<div class="w-tab-pane" data-w-tab="Tab 6">
	 							<g:render template="/fileAdmin/tab6" />
	 						</div>
	 						<div class="w-tab-pane" data-w-tab="Tab 4">
	 							<g:render template="/fileAdmin/tab4" />
	 						</div>
	 						<div class="w-tab-pane" data-w-tab="Tab 5">
	 							<g:render template="/fileAdmin/tab5" />
	 						</div>
	 					</div>       
					</div>
				</div>
			</div>
		</div>
	</body>

</html>


