<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
<meta name="layout" content="mainCoe"/>
<title>${file}</title>
<g:if test="${file?.type == 'Quicktime'}">
    <script type="text/javascript" src="<g:resource dir='jwplayer' file='jwplayer.js'/>"></script>
    <script>jwplayer.key = "${grailsApplication.config.jwID}";</script>
</g:if>
