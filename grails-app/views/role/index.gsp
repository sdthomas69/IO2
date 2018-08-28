
<%@ page import="cev.blackFish.Role" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>List Roles</title>
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

								<g:sortableColumn property="name" title="${message(code: 'role.name.label', default: 'Name')}" />

							</tr>
						</thead>
						<tbody>
						<g:each in="${roleInstanceList}" status="i" var="roleInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

								<td>
									<g:link action="edit" id="${roleInstance.id}">${fieldValue(bean: roleInstance, field: "name")}</g:link>
								</td>

							</tr>
						</g:each>
						</tbody>
					</table>
				</div>
				<div class="pagination">
					<g:paginate total="${roleInstanceCount ?: 0}" />
				</div>
			</div>
		</div>
	</body>
</html>
