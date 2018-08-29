<g:if test="${flash.message}">
    <div data-alert class="alert-box success">
        ${flash.message}
    </div>
</g:if>
<g:hasErrors bean="${menuSetInstance}">
    <div data-alert class="alert-box warning">
        <g:renderErrors bean="${menuSetInstance}" as="list"/>
    </div>
</g:hasErrors>