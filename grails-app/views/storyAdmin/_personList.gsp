<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:form method="post" >
    <input type="hidden" name="id" value="${story.id}" />
	<div class="w-row">
		<g:each in="${story.people.sort()}" var="person" status="i">
			<g:set var="link" value="${createLink(controller: 'user', action: 'home', params: ['firstAndLast' : person?.name])}" />
			<div class="w-col w-col-3">
				<a class="w-inline-block spotlight-link-block" href="">
					<img src="<g:resource file='${person?.primaryImage?.smallImageSquared ?: thumbnail}' />" alt="${person}"/>
				</a>
	        	<div class="spotlight-text">
	        		<g:checkBox name="people" value="${person.id}" checked="false"></g:checkBox> 
	        		${person}
	        	</div>
	        </div>
		</g:each>
	</div>
	<div class="w-row">
		<div class="w-col w-col-3">
			<g:actionSubmit class="button tiny alert" action="RemovePeople" value="Remove Selected People" />
		</div>
	</div>
</g:form>
