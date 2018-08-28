<g:if test="${user?.files*.published?.contains(true)}">
	<div>
		<h3 class="eventstitle">ASSOCIATED&nbsp;FILES</h3>
		<ul class="w-clearfix latest-news-list">
			<g:each in="${user.files.sort()}" var="file">
				<g:if test="${file.published}">
					<li class="latest-news-item">
		           		<a class="w-inline-block" href="<blackFish:setLink object="${file}" name="file" />">
		           			<div class="w-row">
		           				<div class="w-col w-col-3 w-col-small-3 w-col-tiny-3">
		           					<img class="congratsimage latest-news-image" src="<g:resource file='${file.tinyImage ?: file.smallImage}' />" width="64">
		           				</div>
			           			<div class="w-col w-col-9 w-col-small-9 w-col-tiny-9">
			           				<h4 class="latest-news-heading">${file.title}</h4>
			           				<div class="latest-news-text">
			           					<blackFish:shortenText text="${file.shortDescription}" size="50"/> ...
			           				</div>
			           			</div>
			           		</div>
		           		</a>
	            	</li>
            	</g:if>
			</g:each>
		</ul>
	</div>
</g:if>