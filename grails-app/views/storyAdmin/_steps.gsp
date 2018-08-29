<g:set var="current" value="${false}"/>
<g:set var="actions"
       value="${['Text': 'editText', 'Tags': 'associateTag', 'Files': 'associateFile', 'Stories': 'associateStory', 'Menuing': 'editMenuSet', 'Location': 'setLocation', 'Edit': 'edit']}"/>
<g:if test="${story?.id}">
    <div class="w-section admin-steps-menu-section">
        <div class="w-container admin-container">
            <h1 class="story-title">
                <g:link controller="story" action="show" id="${story.id}">
                    ${story.pageTitle}
                </g:link>
            </h1>
            <ul class="w-list-unstyled w-clearfix admin-steps-menu-list">
                <g:each in="${actions}" var="action" status="i">
                    <g:set var="counter" value="${i + 1}"/>
                    <g:set var="current" value="${action == params.action}"/>
                    <g:set var="liClass"
                           value="${params.action == action.value ? 'small button' : 'small button secondary'}"/>
                    <li class="admin-nav-bar-list-item">
                        <g:link class="w-inline-block admin-link steps-button" controller="${controllerName}"
                                action="${action.value}" id="${story.id}">
                            <img class="icon-admin-menu" src="<g:resource dir='images' file='${action.value}.png'/>"
                                 height="35">

                            <div class="admin-link-text-block">${action?.key}</div>
                        </g:link>
                    </li>
                    <g:set var="current" value="${false}"/>
                </g:each>
            </ul>
        </div>
    </div>
</g:if>