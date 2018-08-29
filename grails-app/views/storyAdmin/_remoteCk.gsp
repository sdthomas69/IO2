<g:if test="${searchResult?.getTotalCount() > 0}">
    <table>
        <g:set var="counter" value="${0}"/>
        <g:each var="result" in="${searchResult}" status="i">
            <g:if test="${counter % 3 == 0}">
                <tr>
            </g:if>
            <td>
                <g:link controller="file" action="title" params="['title': result.urlTitle]">
                    <img
                            onclick="getImageData(this)"
                            class="imageborder"
                            src="<g:resource file='${result.mediumImage}'/>"
                            alt="<g:resource file='${result.title}'/>"/>
                </g:link>
            </td>
            <g:if test="${counter % 3 == 2}">
                </tr>
            </g:if>
            <g:set var="counter" value="${counter + 1}"/>
        </g:each>
    </table>
    <g:if test="${searchResult?.getTotalCount() > params.max.toInteger()}">
        <util:remoteNonStopPageScroll
                loadingHTML="spinner"
                heightOffset="50"
                action="remoteImageSearch"
                total="${searchResult?.getTotalCount()}"
                update="list"
                params="${params}"/>
    </g:if>
</g:if>