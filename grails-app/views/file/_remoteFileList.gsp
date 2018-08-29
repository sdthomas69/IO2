<link rel="stylesheet" type="text/css" href="<g:resource dir='css' file='masonryMain.css'/>"/>
<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
<g:if test="${params.view == 'list'}">
    <div class="row">
        <div class="columns large-12">
            <table>
                <thead>
                <tr>
                    <g:sortableColumn
                            property="title"
                            title="Title"
                            params="[
                                    'max'   : params?.max,
                                    'offset': params?.offset,
                                    'type'  : params.type,
                                    'view'  : params.view,
                                    'site'  : params.site
                            ]"/>
                    <g:sortableColumn
                            property="date"
                            title="Date"
                            params="[
                                    'max'   : params?.max,
                                    'offset': params?.offset,
                                    'type'  : params.type,
                                    'view'  : params.view,
                                    'site'  : params.site
                            ]"/>
                </tr>
                </thead>
                <tbody>
                <g:each in="${fileList}" status="i" var="file">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>
                            <g:link controller="file" action="title" params="['title': file.urlTitle]">
                                ${file}
                            </g:link>
                        </td>
                        <td>
                            <g:formatDate format="yyyy-MM-dd" date="${file.date}"/>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
</g:if>


<g:else>

    <g:each in="${fileList}" var="file" status="i">
        <div class="w-col w-col-2 presItem">
            <a class="w-inline-block story-link-div-block" href="<blackFish:setLink object='${file}' name='file'/>">
                <img class="storyimage"
                     src="<g:resource file='${file?.smallImageSquared ?: thumbnail}'/>"
                     alt="${file}"/>
            </a>

            <div class="spotlight-text">
                <a href="<blackFish:setLink object='${file}' name='file'/>">
                    ${file}
                </a>
            </div>
        </div>
    </g:each>

    <div class="w-section">
        <div class="w-container body-text-container single-column admin-column">
            <div id="storyList">
                <g:set var="thumbnail" value="${grailsApplication.config.genericPerson}"/>
                <div class="w-section w-clearfix grid" id="grid">
                    <div class="grid-sizer"></div>
                    <g:each in="${userInstanceList}" var="user" status="i">
                        <div class="item">
                            <g:set var="link"
                                   value="${createLink(controller: 'user', action: 'home', params: ['firstAndLast': user?.name])}"/>
                            <a class="overlay2" href="${link}">
                                <div class="description">
                                    ${user?.firstName} ${user?.lastName}
                                </div>
                                <img class="image"
                                     src="<g:resource file='${user?.primaryImage?.smallImageSquared ?: thumbnail}'/>"
                                     alt="${user}"/>
                            </a>
                        </div>
                    </g:each>
                    <div class="spacerdiv"></div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="<g:resource dir='js' file='masonry.js'/>"></script>
    <script type="text/javascript" src="<g:resource dir='js' file='main.js'/>"></script>

</g:else>

<div class="w-col w-col-12">
    <div class="paginateButtons">
        <util:remotePaginate
                action="remoteFileList"
                total="${fileList?.getTotalCount()}"
                update="list"
                params="${params}"/>
    </div>
</div>

 
 
 
 
