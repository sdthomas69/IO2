<g:if test="${help}">
    <div data-collapse="all" data-animation="over-right" data-duration="400" data-contain="1">
        <a class="w-inline-block admin-link help-modal-link wf-affected wf-selected" href="#">
            <img class="icon-admin-menu" src="<g:resource dir='images' file='intranet-white.png'/>" height="35">

            <div class="admin-link-text-block">
                Help
            </div>
        </a>
    </div>

    <div class="w-section help-modal-background">
        <div class="w-clearfix help-modal-window">
            <div class="w-col w-col-12 text-col">
                <a class="help-close" href="#">Close</a>

                <h2>${help.title}</h2>
                ${help.description.encodeAsRaw()}
                <g:if test="${help?.primaryImage?.type == 'Image'}">
                    <img class="th" src="<g:resource file='${help?.primaryImage?.mediumImage}'/>"
                         alt="${help.primaryImage}"/>
                </g:if>
            <%--<g:if test="${help?.primaryImage?.type == 'Quicktime'}">
              <div class="flex-video">
                  <iframe width="420" height="315" src="${help.primaryImage.path}" frameborder="0" allowfullscreen></iframe>
                </div>
            </g:if>--%>
                <shiro:hasPermission permission="help:edit:${help.id}">
                    <g:link controller="help" action="edit" id="${help.id}">Edit</g:link>
                </shiro:hasPermission>
            </div>
        </div>
    </div>
</g:if>
<g:if test="${!help}">
    <g:link
            class="button success tiny"
            action="create"
            controller="help"
            params="['selectedController': selectedController, 'activity': activity]">
        Create Help
    </g:link>
</g:if>

