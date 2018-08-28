<%@ page import="org.springframework.util.ClassUtils" %>

<g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}" />
<g:set var="defaultPerson" value="${grailsApplication.config.genericPerson}" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="${params.site ?: 'main'}" />
        <title>Search</title>
    </head>
    <body class="story">
        <div class="grid_16">
			<table align="left">
				<tr>
					<td>
                        <g:form url='[controller: params.object, action: 'search']' id="searchableForm" name="searchableForm" method="get">
                            <g:textField name="q" value="${params?.q?.encodeAsHTML()}"/> &nbsp;
                            <input type="submit" value="Search" />
                        </g:form>
					</td>
				</tr>
			</table>
		</div>
		<div id="smallThumbs160" class="grid_16">

            <div id="content" class="container_16">
                <div id="smallThumbs160" class="grid_16">
                    <div class="spacer">&nbsp;</div>
                    <g:each var="result" in="${searchResult}" status="i">
                        <g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
                        <g:set var="link" value="${createLink(controller: className[0].toLowerCase() + className[1..-1], action: 'show', id: result.id)}" />
                        <div class='<blackFish:setClass counter="${i}" width="8" beginDiv="grid_2 alpha" middleDiv="grid_2" endDiv="grid_2 omega" />'>
                            
                            <g:if test="${className == 'File'}">
                                <g:link controller="file" action="title" params="['title' : result]"><img class="imageborder" src="<g:resource file='${result.smallImage}' />" alt="" width="98" border="0"/></g:link>
                                <g:link controller="file" action="title" params="['title' : result]"><p class="imagecaption">${result}</p></g:link>
                            </g:if>
                            
                            <g:elseif test="${className == 'Event'}">
                                <g:link controller="event" action="title" params="['title' : result]"><p class="imagecaption">${result}</p></g:link>
                            </g:elseif>
                            
                            <g:elseif test="${className == 'Story'}">
                                <g:link controller="story" action="title" params="['title' : result]"><img class="imageborder" src="<g:resource file='${result?.primaryImage?.smallImage ?: thumbnail}' />" alt="" width="98" border="0"/></g:link>
                                <g:link controller="story" action="title" params="['title' : result]"><p class="imagecaption">${result}</p></g:link>
                            </g:elseif>
                            
                        </div>
                        <g:if test="${i % 8 == 7}">
                            <div class="middleSpacer"></div>
                        </g:if>
                    </g:each>
                    <div class="spacer">&nbsp;</div>
                </div>
            </div>
            
            <div class="paginateButtons">
                <g:if test="${searchResult?.getTotalCount() > params?.max}">
					<div class="paginateButtons">                           
						<g:paginate total="${searchResult.getTotalCount()}" params="${params}" />
					</div>
				</g:if>
            </div>
         </div>
    </body>
</html>
