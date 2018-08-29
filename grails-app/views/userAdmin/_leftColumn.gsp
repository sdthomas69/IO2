<div class="info-block">
    <img width="200" src="<g:resource file='${userInstance?.primaryImage?.smallImage ?: genericPerson}'/>"
         class="personimage">
    <g:if test="${userInstance.primaryImage}">
        <g:link class="w-button adminbutton" params="['id': userInstance.id, 'fileId': userInstance.primaryImage.id]"
                action="unSetPrimaryImage">
            Remove
        </g:link>
    </g:if>
    <g:if test="${!userInstance.primaryImage}">
        <g:link class="w-button submit-button" controller="fileAdmin"
                params="['author.id': userInstance?.id, 'portrait': 'true']" action="create">
            +&nbsp;Add A Portrait
        </g:link>
    </g:if>
    <h2 class="personheading">${userInstance.name}</h2>
</div>