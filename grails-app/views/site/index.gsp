
<%@ page import="cev.blackFish.Site" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main" />
		<title>Site List</title>
	</head>
	<body>
		<div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
				<g:render template="adminInfo" />
				<g:render template="messages" />
				<div class="datagrid">
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="title" title="${message(code: 'site.title.label', default: 'Title')}" />
							
								<g:sortableColumn property="styleSheet" title="${message(code: 'site.styleSheet.label', default: 'Style Sheet')}" />
							
								<th><g:message code="site.portal.label" default="Portal" /></th>
							
								<th><g:message code="site.bottom.label" default="Bottom" /></th>
							
								<th><g:message code="site.main.label" default="Main" /></th>
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${siteInstanceList}" status="i" var="siteInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="edit" id="${siteInstance.id}">${fieldValue(bean: siteInstance, field: "title")}</g:link></td>
							
								<td>${fieldValue(bean: siteInstance, field: "styleSheet")}</td>
							
								<td>${fieldValue(bean: siteInstance, field: "portal")}</td>
							
								<td>${fieldValue(bean: siteInstance, field: "bottom")}</td>
							
								<td>${fieldValue(bean: siteInstance, field: "main")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
				</div>
				<div class="pagination">
					<g:paginate total="${siteInstanceCount ?: 0}" />
				</div>
			</div>
		</div>
	</body>
</html>
