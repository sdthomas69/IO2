<g:if test="${story?.parent?.children}">
    <g:set var="hasSiblings" value="${true}"/>
</g:if>
<g:if test="${story?.children}">
    <g:set var="hasChildren" value="${true}"/>
</g:if>
<g:if test="${story?.parent}">
    <g:set var="hasParent" value="${true}"/>
</g:if>
<g:if test="${hasParent || hasSiblings || hasChildren}">
    <div class="w-nav sidebar-nav" data-collapse="none" data-animation="default" data-duration="400" data-contain="1">
        <div class="w-container sidebar-nav-container">
            <nav class="w-nav-menu w-clearfix sidebar-nav-menu" role="navigation">
                <g:if test="${hasParent}">
                    <a class="w-nav-link sidebar-nav-link"
                       href="<blackFish:setLink object='${story.parent}' name='story'/>">
                        ${story.parent.title}
                    </a>
                </g:if>
                <g:if test="${hasSiblings}">
                    <g:render template="/story/menu" collection="${story.parent.children.sort()}" var="thisMenu"/>
                </g:if>
                <g:elseif test="${hasChildren}">
                    <g:render template="/story/menu" collection="${story.children.sort()}" var="thisMenu"/>
                </g:elseif>
            </nav>

            <div class="w-nav-button">
                <div class="w-icon-nav-menu"></div>
            </div>
        </div>
    </div>
</g:if>
