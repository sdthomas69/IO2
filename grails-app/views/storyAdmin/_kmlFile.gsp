<h4 class="storyTitle">KML Location File</h4>
<g:link controller='file' action='show' id='${story.kmlFile.id}'>
	<img class="imageborder" src="<g:resource file='${story.kmlFile.smallImage}' />" alt="${story.kmlFile}" />
</g:link>
<p>
	<a href="#" data-reveal-id="e2ops" class="button success tiny">Options</a>
	<g:link controller='file' action='show' id='${story.kmlFile.id}'>${story.kmlFile}</g:link>
</p>
<div id="e2ops" class="reveal-modal" data-reveal>
	<p>
	    <g:link class="button tiny success"
	    	params='["id":story.id, "files":story.kmlFile.id]' 
	    	action='addFiles'>
	    	Associate
	    </g:link>
	    <a href="#" data-reveal-id="e2" class="button tiny">HTML</a>
		<g:link class="button tiny alert"
			params='["id":story.id, "fileId":story.kmlFile.id]' 
			action='removeKmlFile'>
			Remove
		</g:link>
		<a class="close-reveal-modal">&#215;</a>
	</p>
</div>
<div id="e2" class="reveal-modal" data-reveal>
	<textarea rows="4" cols="25" id="e2Link">
	&lt;a href="/file/${story.kmlFile.urlTitle}"&gt;
		&lt;img class="imageborder" src="${story.kmlFile.smallImage}" alt="${story.kmlFile}" /&gt;
	&lt;/a&gt;
	</textarea>
	<a class="close-reveal-modal">&#215;</a>
</div>