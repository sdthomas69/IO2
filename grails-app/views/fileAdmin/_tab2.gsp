<g:form method="post">
    <input type="hidden" name="id" value="${file?.id}"/>
    <input type="hidden" name="version" value="${file?.version}"/>

    <div class="w-row">
        <div class="w-col w-col-6">
            <label for="tinyImage">
                <a
                        href="${file.tinyImage}"
                        target="_blank">
                    ${grailsApplication.config.tinyIconHeight}x${grailsApplication.config.tinyIconWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="tinyImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'tinyImage', 'errors')}"
                    name="tinyImage" data-name="tinyImage"
                    value="${fieldValue(bean: file, field: 'tinyImage')}">
            <label for="smallImage">
                <a
                        href="${file.smallImage}"
                        target="_blank">${grailsApplication.config.smIconWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="smallImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'smallImage', 'errors')}"
                    name="smallImage"
                    data-name="smallImage"
                    value="${fieldValue(bean: file, field: 'smallImage')}">
            <label for="smallImageSquared">
                <a
                        href="${file.smallImageSquared}"
                        target="_blank">
                    ${grailsApplication.config.smIconHeight}x${grailsApplication.config.smIconWidth}
                </a>
            </label>
            <input
                    type="text" id="smallImageSquared"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'smallImageSquared', 'errors')}"
                    name="smallImageSquared" data-name="smallImageSquared"
                    value="${fieldValue(bean: file, field: 'smallImageSquared')}">
            <label for="inBetweenImage">
                <a
                        href="${file.inBetweenImage}"
                        target="_blank">
                    ${grailsApplication.config.inBetweenIconWidth}
                </a>
            </label>
            <input type="text"
                   id="inBetweenImage"
                   class="w-input admin-text-field value ${hasErrors(bean: file, field: 'inBetweenImage', 'errors')}"
                   name="inBetweenImage" data-name="inBetweenImage"
                   value="${fieldValue(bean: file, field: 'inBetweenImage')}">
            <label for="mediumImage">
                <a
                        href="${file.mediumImage}"
                        target="_blank">
                    ${grailsApplication.config.medIconLength}
                </a>
            </label>
            <input
                    type="text"
                    id="mediumImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'mediumImage', 'errors')}"
                    name="mediumImage" data-name="mediumImage"
                    value="${fieldValue(bean: file, field: 'mediumImage')}">
            <label for="largeImage">
                <a
                        href="${file.largeImage}"
                        target="_blank">
                    ${grailsApplication.config.largeIconLength}
                </a>
            </label>
            <input
                    type="text"
                    id="largeImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'largeImage', 'errors')}"
                    name="largeImage" data-name="largeImage"
                    value="${fieldValue(bean: file, field: 'largeImage')}">
        </div>

        <div class="w-col w-col-6">
            <label for="xLargeImage">
                <a
                        href="${file.xLargeImage}"
                        target="_blank">
                    ${grailsApplication.config.xLargeIconLength}
                </a>
            </label>
            <input
                    type="text" id="xLargeImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'xLargeImage', 'errors')}"
                    name="xLargeImage" data-name="xLargeImage"
                    value="${fieldValue(bean: file, field: 'xLargeImage')}">
            <label for="smallSlideImage">
                <a
                        href="${file.smallSlideImage}"
                        target="_blank">
                    ${grailsApplication.config.smallSlideHeight}x${grailsApplication.config.smallSlideWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="smallSlideImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'smallSlideImage', 'errors')}"
                    name="smallSlideImage"
                    data-name="smallSlideImage"
                    value="${fieldValue(bean: file, field: 'smallSlideImage')}">
            <label for="slideImage">
                <a
                        href="${file.slideImage}"
                        target="_blank">
                    ${grailsApplication.config.slideHeight}x${grailsApplication.config.slideWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="slideImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'slideImage', 'errors')}"
                    name="slideImage" data-name="slideImage"
                    value="${fieldValue(bean: file, field: 'slideImage')}">
            <label for="mediumSlideImage">
                <a
                        href="${file.mediumSlideImage}"
                        target="_blank">
                    ${grailsApplication.config.mediumSlideHeight}x${grailsApplication.config.mediumSlideWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="mediumSlideImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'mediumSlideImage', 'errors')}"
                    name="mediumSlideImage"
                    data-name="mediumSlideImage"
                    value="${fieldValue(bean: file, field: 'mediumSlideImage')}">
            <label for="xSlideImage">
                <a
                        href="${file.xSlideImage}"
                        target="_blank">
                    ${grailsApplication.config.xSlideHeight}x${grailsApplication.config.xSlideWidth}
                </a>
            </label>
            <input
                    type="text"
                    id="xSlideImage"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'xSlideImage', 'errors')}"
                    name="xSlideImage" data-name="xSlideImage"
                    value="${fieldValue(bean: file, field: 'xSlideImage')}">
            <label for="path">
                <a
                        href="${file.path}"
                        target="_blank">
                    Path
                </a>
            </label>
            <input
                    type="text"
                    id="path"
                    class="w-input admin-text-field value ${hasErrors(bean: file, field: 'path', 'errors')}"
                    name="path"
                    data-name="path"
                    value="${fieldValue(bean: file, field: 'path')}">
        </div>
    </div>
    <g:link class="w-button submit-button" params='["id": file?.id]' action='swapIcon'>Add Custom Icon</g:link>
    <g:link class="w-button submit-button" params='["id": file?.id]' action='swap'>Replace File on Server</g:link>
    <g:actionSubmit class="w-button submit-button" action="Update" value="Update"/>
</g:form>