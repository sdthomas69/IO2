<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:set var="genericPerson" value="${grailsApplication.config.genericPerson}" />
<html>
    <head>
        <title>Edit</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <g:render template="adminInfo" />
		<g:render template="messages" />
        <div class="w-section">
			<div class="w-container intranet-container">
				<g:render template="leftColumn" />
				<div class="form-block">
					<div data-duration-in="300" data-duration-out="100" class="w-tabs admin-text-tabs">
						<div class="w-tab-menu admin-tabs-menu-container">
							<a data-w-tab="Personal" class="w-tab-link ${flash.tab == null ? 'w--current' : ''} w-inline-block tab-link-button">
			                	<div class="intranet-text-block">Personal Information</div>
			                </a>
			                <a data-w-tab="Files" class="w-tab-link ${flash.tab == 'files' ? 'w--current' : ''} w-inline-block tab-link-button">
			                  	<div>Files</div>
			                </a>
			                <a data-w-tab="Stories" class="w-tab-link ${flash.tab == 'stories' ? 'w--current' : ''} w-inline-block tab-link-button">
			                  	<div>Stories</div>
			                </a>
			                %{--<a data-w-tab="Tags" class="w-tab-link ${flash.tab == 'tags' ? 'w--current' : ''} w-inline-block tab-link-button">
			                  	<div>Tags</div>
			                </a>--}%
						</div>
						<div class="w-tab-content admin-tabs-container">
							
							<div data-w-tab="Personal" class="w-tab-pane ${flash.tab == null ? 'w--tab-active' : ''}">
								<g:render template="/userAdmin/personal" />
							</div>
							
							<div data-w-tab="Stories" class="w-tab-pane ${flash.tab == 'stories' ? 'w--tab-active' : ''}">
								<h2 class="userheading">Stories</h2>
								<g:link class="w-button submit-button" controller="storyAdmin" params="['author.id':userInstance?.id]" action="createText">
									+&nbsp;Add A New Story
								</g:link>
								<g:render template="storyList" />
							</div>

							<div data-w-tab="Files" class="w-tab-pane ${flash.tab == 'files' ? 'w--tab-active' : ''}">
								<g:link class="w-button submit-button" controller="fileAdmin" params="['author.id':userInstance?.id]" action="create">
									+&nbsp;Add A New File
								</g:link>
								<g:render template="fileList" />
							</div>
							
							%{--<div data-w-tab="Tags" class="w-tab-pane ${flash.tab == 'tags' ? 'w--tab-active' : ''}">
								<g:link class="w-button submit-button" controller="tagAdmin" params="['user.id':userInstance?.id]" action="create">
									+&nbsp;Add A New Tag
								</g:link>
								<g:render template="tagList" />
							</div>--}%
						</div>
					</div>
				</div>
			</div>
		</div>
    </body>
    
</html>
