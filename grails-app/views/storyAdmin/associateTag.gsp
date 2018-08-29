<html><head>    <title>Associate Tag</title>    <meta name="layout" content="main"/></head><body><g:render template="/storyAdmin/adminInfo"/><g:render template="errors"/><g:render template="steps"/><div class="w-section">    <div class="w-container body-text-container primary-small contains-admin-nav-bar">        <h2>Add or Create Tags</h2>        <div class="w-form">            <g:if test="${story.tags}">                <label for="Search-Tags">Associated Tags:</label>                <g:form method="post">                    <input type="hidden" name="id" value="${story.id}"/>                    <ul class="w-list-unstyled w-clearfix tags-list">                        <g:each in="${story.tags}" var="tag">                            <li class="tag-list-item">                                <div class="w-checkbox w-clearfix tag-link-checkbox">                                    <g:checkBox class="w-checkbox-input tag-checkbox" name="tags" data-name="tags"                                                value="${tag.id}" checked="false"></g:checkBox>                                    <label class="w-form-label" for="checkbox">                                        <g:link class="tag-heading" action="show" controller="tag" id="${tag.id}">                                            ${tag}                                        </g:link>                                    </label>                                </div>                            </li>                        </g:each>                    </ul>                    <g:actionSubmit class="w-button submit-button" action="RemoveTags" value="Remove Selected Tags"/>                </g:form>            </g:if>            <div class="w-row">                <div class="w-col w-col-8 w-clearfix">                    <label for="Search-Tags">Search Tags:</label>                    <g:formRemote                            url='[controller: "storyAdmin", action: "tagSearch", id: story.id]'                            update="[success: 'list', failure: 'error']"                            name="remoteForm"                            id="${story.id}"                            method="get">                        <g:textField class="w-input admin-text-field tag-search" id="search_query" name="q"                                     value="${params?.q?.encodeAsHTML()}"/>                        <input class="w-button button tag-search" type="submit" value="Search"/>                    </g:formRemote>                    <div id="spinner" class="spinner1" style="display:none;">                        <img src="<g:resource file='images/spinner.gif'/>" alt="Spinner"/>                    </div>                    <div id="list"></div>                    <div id="error"></div>                </div>                <div class="w-col w-col-4 tag-col2">                    <g:link class="button tags-button" action="create" controller="tagAdmin"                            params="['story.id': story.id]">                        Create a new Tag                    </g:link>                </div>            </div>        </div>    </div></div></body></html>