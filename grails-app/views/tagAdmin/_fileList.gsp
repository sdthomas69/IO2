<g:if test="${tagInstance.files}">
    <g:form method="post">
        <input type="hidden" name="id" value="${tagInstance?.id}"/>
        <input type="hidden" name="version" value="${tagInstance?.version}"/>

        <div class="w-row">
            <g:each in="${tagInstance.files}" var="file" status="i">
                <div class="w-col w-col-3">
                    <g:link class="w-inline-block spotlight-link-block" controller='file' action='show' id='${file.id}'>
                        <img src="<g:resource file='${file?.smallImageSquared}'/>" alt="${file}"/>
                    </g:link>
                    <div class="spotlight-text">
                        <g:checkBox name="files" value="${file.id}" checked="false"></g:checkBox>
                        ${file}
                    </div>
                </div>
            </g:each>
        </div>

        <div class="w-row">
            <g:actionSubmit class="w-button submit-button" action="RemoveFiles" value="Remove Selected Files"/>
        </div>
    </g:form>
</g:if>
