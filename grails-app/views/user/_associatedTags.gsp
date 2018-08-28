<g:if test="${user.tags}">
	<div class="latest-news-div-block">
		<h3 class="eventstitle">Research Interests</h3>
		<ul class="w-list-unstyled w-clearfix latest-news-list">
			<g:each in="${user.tags}" var="tag">
				<li class="latest-news-item tag">
					<g:link class="tag-heading" controller="tag" action="title" params="['selectedTag' : tag.urlTitle]">
						${tag}
					</g:link>
				</li>
			</g:each>
		</ul>
	</div>
</g:if>