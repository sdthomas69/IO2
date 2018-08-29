<g:if test="${shiroUser.files}">
    <!-- Related Content -->
    <g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
    <div id="story_highlights">
        <h3 class="section">Files</h3>

        <div id="tinyThumbs100">
            <g:each in="${shiroUser.files?.sort()}" var="file" status="i">
                <div class='<blackFish:setClass counter="${i}" width="4" beginDiv="grid_1 alpha" middleDiv="grid_1"
                                                endDiv="grid_1 omega"/>'>
                    <g:link action="show" controller="file" id="${file.id}">
                        <img src="<g:resource file='${file.tinyImage ?: file.smallImage}'/>" class="imageborder"
                             alt="${file}" width="36" height="36" border="0">
                    </g:link>
                </div>
                <g:if test="${i % 4 == 3}">
                    <div class="middleSpacer"></div>
                </g:if>
            </g:each>
        </div>

        <div class="clear"></div>
    </div>
</g:if>