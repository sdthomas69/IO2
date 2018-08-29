<g:if test="${flash.message}">
    <div data-alert class="alert-box success">
        ${flash.message}
    </div>
</g:if>
<g:hasErrors bean="${permissionInstance}">
    <div data-alert class="alert-box warning">
        <g:renderErrors bean="${permissionInstance}" as="list"/>
    </div>
</g:hasErrors>