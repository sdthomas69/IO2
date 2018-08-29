<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
<meta name="layout" content="main"/>
<meta property="og:title" content="${story}"/>
<meta property="og:url" content="${request.serverName}${request.forwardURI}"/>
<meta property="og:description" content="${story.shortDescription}"/>
<meta property="og:type" content="article"/>
<g:if test="${story?.primaryImage}">
    <meta property="og:image" content="${story.primaryImage?.mediumSlideImage}"/>
</g:if>
<title>${story}</title>
<g:if test="${story?.primaryImage?.type == 'Quicktime' || story.hasVideos()}">
    <script type="text/javascript" src="<g:resource dir='jwplayer' file='jwplayer.js'/>"></script>
    <script type="text/javascript" src="<g:resource file='json/videos/story/${story.id}'/>"></script>
</g:if>
<g:if test="${story?.primaryImage?.type == 'KML' || story?.primaryImage?.type == 'KMZ'}">
    <g:set var="url" value="${request.serverName}${story?.primaryImage?.path}" scope="request"/>
    <g:render template="/common/gEarthJs"/>
</g:if>
<g:if test="${story.headScript}">
    ${story.headScript}
</g:if>
