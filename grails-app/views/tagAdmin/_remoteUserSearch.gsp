<g:if test="${searchResult?.getTotalCount() > 0}">
    <g:set var="thumbnail" value="${grailsApplication.config.genericPerson}"/>
    <div class="w-row">
        <div class="w-col w-col-9 w-clearfix description-text-column">
            <g:each var="result" in="${searchResult}" status="i">
                <blackFish:tagWrapper
                        tag="div"
                        className="w-row file-list-row"
                        iterations="4"
                        counter="${i}"
                        total="${searchResult?.getTotalCount()}">
                    <div class="w-col w-col-3 spacing">
                        <g:link controller='userAdmin' action='show' id='${result.id}'>
                            <img
                                    src="<g:resource file='${result?.primaryImage?.smallImageSquared ?: thumbnail}'/>"
                                    alt="${result}"/>
                        </g:link>
                        <div class="file-list-text">
                            <g:checkBox name="users" value="${result.id}" checked="false"></g:checkBox> ${result}
                        </div>
                    </div>
                </blackFish:tagWrapper>
            </g:each>
        </div>

        <div class="w-col w-col-3 sidebar-right totop">
            <g:actionSubmit class="w-button submit-button" action="AddUsers" value="Add Selected Users"/>
        </div>
    </div>
    <util:remoteNonStopPageScroll
            loadingHTML="spinner"
            heightOffset="150"
            action="userSearch"
            total="${searchResult?.getTotalCount()}"
            update="list2"
            params="${params}"/>
</g:if>
<g:else><p>There are no results for your query</p></g:else>
