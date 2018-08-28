<g:link controller='file' action='show' id='${story.embeddedImage2.id}'>
	<g:if test="${story.embeddedImage2.type == 'Image'}">
		<img src="<g:resource file='${story.embeddedImage2.smallSlideImage}' />" alt="${story.embeddedImage2}"/>
	</g:if>
	<g:else>
		<img src="<g:resource file='${story.embeddedImage2.tinyImage}' />" alt="" width="96" border="0"/>
	</g:else>
</g:link>
<ul class="w-list-unstyled w-clearfix admin-nav-bar-list file-edit-list">
	<li class="admin-nav-bar-list-item">
		<g:link 
			title="Remove"
			alt="Remove"
			class="w-inline-block admin-link red" 
	    	controller='storyAdmin' 
	    	params='["id":story.id, "fileId":story.embeddedImage2.id]' 
	    	action='unEmbedImage2'>
	    	<div class="admin-link-text-block file-button">
	    		Remove
	    	</div>
	    </g:link>
    </li>
    <li class="admin-nav-bar-list-item">
		<g:link 
			title="Set as EmbeddedImage 1"
			alt="Set as EmbeddedImage 1"
			class="w-inline-block admin-link"
			controller='storyAdmin' 
			params='["id":story.id, "fileId":story.embeddedImage2.id]' 
			action='embedImage1'>
			<div class="admin-link-text-block file-button">
				E1
			</div>
		</g:link>
	</li>
	<li class="admin-nav-bar-list-item">
		<g:link 
			title="Associate"
			alt="Associate"
			class="w-inline-block admin-link"
			controller='storyAdmin' 
			params='["id":story.id, "files":story.embeddedImage2.id]' 
			action='addFiles'>
			<div class="admin-link-text-block file-button">
				Associate
			</div>
		</g:link>
	</li>
</ul>