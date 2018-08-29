<g:if test="${userInstance.events.size() > 0}">
    <g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
    <div class="thumbnailContainer">
        <div class="spacer">&nbsp;</div>
        <g:each in="${userInstance.events.sort()}" var="event" status="i">
            <div class="float">
                <g:link action="show" controller="event" id="${event.id}">
                    <img class="imageborder" src="<g:resource file='${event?.primaryImage?.smallImage ?: thumbnail}'/>"
                         width="150" border="0"/>
                </g:link>
                <p class="imagecaption"><g:link action="show" controller="event" id="${event.id}">${event}</g:link><br/>

                <p>
                    <shiro:hasPermission permission="permission:getUserPermissions">
                        <g:remoteLink action="getUserPermissions" controller="permission"
                                      update="[success: 'list2', failure: 'error2']"
                                      params="['objectId': event.id, 'object': 'eventAdmin']"
                                      id="${userInstance.id}">Permissions</g:remoteLink><br/>
                    </shiro:hasPermission>
                </p>

                <div id="list2"></div>

                <div id="error2"></div>
            </div>
            <g:if test="${i % 4 == 3}">
                <div class="middleSpacer"></div>
            </g:if>
        </g:each>
        <div class="spacer">&nbsp;</div>
    </div>
</g:if>