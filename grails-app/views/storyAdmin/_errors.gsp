<g:if test="${flash.message}">
    <div data-alert class="alert-box success">
        ${flash.message}
    </div>
</g:if>
<g:hasErrors bean="${story}">
    <div data-alert class="alert-box warning">
        <g:renderErrors bean="${story}" as="list"/>
    </div>
</g:hasErrors>