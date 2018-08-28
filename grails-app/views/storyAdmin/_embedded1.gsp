<g:link controller='file' action='show' id='${story.embeddedImage1.id}'>
	<g:if test="${story.embeddedImage1.type == 'Image'}">
		<img src="<g:resource file='${story.embeddedImage1.smallSlideImage}' />" alt="${story.embeddedImage1}"/>
	</g:if>
	<g:else>
		<img src="<g:resource file='${story.embeddedImage1.tinyImage}' />" alt="" width="96" border="0"/>
	</g:else>
</g:link>
<ul class="w-list-unstyled w-clearfix admin-nav-bar-list file-edit-list">
	<li class="admin-nav-bar-list-item">
		<g:link 
			title="Remove"
			alt="Remove"
			class="w-inline-block admin-link red" 
	    	controller='storyAdmin' 
	    	params='["id":story.id, "fileId":story.embeddedImage1.id]' 
	    	action='unEmbedImage1'>
	    	<div class="admin-link-text-block file-button">
	    		Remove
	    	</div>
	    </g:link>
    </li>
    <li class="admin-nav-bar-list-item">
		<g:link 
			title="Set as EmbeddedImage 2"
			alt="Set as EmbeddedImage 2"
			class="w-inline-block admin-link"
			controller='storyAdmin' 
			params='["id":story.id, "fileId":story.embeddedImage1.id]' 
			action='embedImage2'>
			<div class="admin-link-text-block file-button">
				E2
			</div>
		</g:link>
	</li>
	<li class="admin-nav-bar-list-item">
		<g:link 
			title="Associate"
			alt="Associate"
			class="w-inline-block admin-link"
			controller='storyAdmin' 
			params='["id":story.id, "files":story.embeddedImage1.id]' 
			action='addFiles'>
			<div class="admin-link-text-block file-button">
				Associate
			</div>
		</g:link>
	</li>
</ul>
