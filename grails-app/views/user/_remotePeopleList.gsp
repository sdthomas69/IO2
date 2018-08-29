<g:set var="thumbnail" value="${grailsApplication.config.genericPerson}"/>
<div class="w-row">
    <g:each in="${userInstanceList}" var="user" status="i">
        <blackFish:tagWrapper
                tag="div"
                className="w-row file-list-row"
                iterations="4"
                counter="${i}"
                total="${userInstanceList.getTotalCount()}">
            <g:set var="link"
                   value="${createLink(controller: 'user', action: 'home', params: ['firstAndLast': user?.name])}"/>
            <div class="w-col w-col-3">
                <a href="${link}">
                    <img class="storyimage"
                         src="<g:resource file='${user?.primaryImage?.smallImageSquared ?: thumbnail}'/>"
                         alt="${user}"/>

                    <div class="file-list-text">
                        ${user?.firstName} ${user?.lastName}
                    </div>
                </a>
            </div>
        </blackFish:tagWrapper>
    </g:each>
    <div class="spacerdiv"></div>
    <g:paginate
            action="${actionName}"
            total="${userInstanceList.getTotalCount()}"
            params="${params}"/>
</div>

