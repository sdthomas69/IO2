<g:if test="${storyResult || fileResult}">
    <g:if test="${storyResult}">
        <h3 class="modalTitle">Stories</h3>
        <g:render template="dropDown" model="[objects: storyResult]"/>
    </g:if>
    <g:if test="${fileResult}">
        <h3 class="modalTitle">Files</h3>
        <g:render template="dropDown" model="[objects: fileResult]"/>
    </g:if>
    <div id="error"></div>

    <div id="spinner" class="spinner1" style="display:none;">
        <img src="<g:resource file='images/spinner.gif'/>" alt="Searching..."/>
    </div>
    <g:link controller="search" action="index" params="['q': params.q]">
        All Results
    </g:link>
</g:if>
<g:if test="${params.q && !fileResult && !storyResult}">
    <p>There are no results for your query</p>
</g:if>