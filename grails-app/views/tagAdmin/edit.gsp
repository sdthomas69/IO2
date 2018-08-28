<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:set var="tab" value="${flash.tab ?: 'default'}" />

<html>
    <head>
        <title>Edit Tag</title>
		<meta name="layout" content="main" />
		<g:javascript plugin="remote-pagination" library="remoteNonStopPageScroll"/>
    </head>
    
    <body>
		<g:render template="/tagAdmin/adminInfo" />
		<g:render template="/tagAdmin/errors" />
    	<div class="w-section">
	   		<div class="w-container body-text-container primary-small contains-admin-nav-bar">
	   			<h2 class="story-title">
	   				<g:link controller="tag" action="show" id="${tagInstance.id}">${tagInstance}</g:link>
	   			</h2>
	   			<%--<shiro:hasPermission permission="permission:findPermissionsByObject">
					<g:link controller="permission" action="findPermissionsByObject" params="['objectName':'tagAdmin', 'objectId': tagInstance.id]">
						Permissions ${previousAction}
					</g:link>
				</shiro:hasPermission>--%>
				<div class="w-tabs admin-text-tabs" data-duration-in="300" data-duration-out="100">
					
					<div class="w-tab-menu admin-tabs-menu-container">
						<a class="w-tab-link ${flash.tab == null ? 'w--current' : ''} w-inline-block tab-link-button" data-w-tab="Tab 1">
							<div>1. Title</div>
						</a>
						<a class="w-tab-link ${flash.tab == 'stories' ? 'w--current' : ''} w-inline-block tab-link-button" data-w-tab="Tab 2">
							<div>2. Stories</div>
						</a>
						<a class="w-tab-link ${flash.tab == 'files' ? 'w--current' : ''} w-inline-block tab-link-button" data-w-tab="Tab 3">
							<div>3. Files</div>
						</a>
						<a class="w-tab-link ${flash.tab == 'users' ? 'w--current' : ''} w-inline-block tab-link-button" data-w-tab="Tab 4">
							<div>4. People</div>
						</a>
					</div>
				
					<div class="w-tab-content admin-tabs-container">
						
						<div class="w-tab-pane ${flash.tab == null ? 'w--tab-active' : ''}" data-w-tab="Tab 1">
							<g:render template="tab1" />
						</div>
					
						<div class="w-tab-pane ${flash.tab == 'stories' ? 'w--tab-active' : ''}" data-w-tab="Tab 2">
				   			<g:render template="tab2" />
				   		</div>
				   		
				   		<div class="w-tab-pane ${flash.tab == 'files' ? 'w--tab-active' : ''}" data-w-tab="Tab 3">
				   			<g:render template="tab3" />
				   		</div>
				   		
				   		<div class="w-tab-pane ${flash.tab == 'users' ? 'w--tab-active' : ''}" data-w-tab="Tab 4">
				   			<g:render template="tab4" />
				   		</div>
			   		
			   		</div>
			   	</div>
	   		</div>
	   	</div>
	</body>
    
</html>
