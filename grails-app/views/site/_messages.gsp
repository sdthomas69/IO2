<g:if test="${flash.message}">
	<div data-alert class="alert-box success">
		${flash.message}
	</div>
</g:if>
<g:hasErrors bean="${siteInstance}">
	<div data-alert class="alert-box warning">
		<g:renderErrors bean="${siteInstance}" as="list" />
	</div>
</g:hasErrors>