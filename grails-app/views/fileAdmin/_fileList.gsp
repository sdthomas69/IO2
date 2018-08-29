<g:form method="post">
    <input type="hidden" name="id" value="${file?.id}"/>
    <input type="hidden" name="version" value="${file?.version}"/>
    <g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
    <g:each in="${file.files}" var="thisFile" status="i">
        <g:link class="w-inline-block grid-item" controller='file' action='show' id='${thisFile.id}'>
            <img src="<g:resource file='${thisFile?.smallImage ?: thumbnail}'/>"/>
        </g:link><br/>
        <g:checkBox name="files" value="${thisFile.id}" checked="false"></g:checkBox>
        <g:link controller='file' action='show' id='${thisFile.id}'>${thisStory}</g:link>
    </g:each>
    <g:actionSubmit class="w-button submit-button" action="RemoveFiles" value="Remove Selected Files"/>
</g:form>