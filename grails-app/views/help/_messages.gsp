<g:if test="${flash.message}">
	<div data-alert class="alert-box success">
		<g:message message="${flash.message}" transparent="true" />
	</div>
</g:if>
<g:hasErrors bean="${helpInstance}">
	<div data-alert class="alert-box warning">
		<g:renderErrors bean="${helpInstance}" as="list" />
	</div>
</g:hasErrors>