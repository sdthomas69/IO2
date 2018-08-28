<%@ page import="cev.blackFish.MenuSet" %>

<html>
	<head>
		<meta name="layout" content="main" />
        <title>Menus</title>
	</head>
	
	<body>
        <div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
				<g:render template="adminInfo" />
				<g:render template="messages" />
				<h1>Menu Bars</h1>
				<div class="datagrid">
					<table>
						<thead>
							<tr>

	<%--							<g:sortableColumn property="title" title="${message(code: 'menuSet.title.label', default: 'Title')}" />--%>

							</tr>
						</thead>
						<tbody>
						<g:each in="${menuSetInstanceList}" status="i" var="menuSetInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

								<td><g:link action="show" id="${menuSetInstance.id}">${fieldValue(bean: menuSetInstance, field: "title")}</g:link></td>

							</tr>
						</g:each>
						</tbody>
					</table>
				</div>
				<div class="pagination">
					<g:paginate total="${menuSetInstanceTotal}" />
				</div>
			</div>
		</div>
	</body>
	
</html>
