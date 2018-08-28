
<!doctype html>
<html>

<head>
	<meta name="layout" content="main">
	<title>List Permissions</title>
</head>

<body>
<g:render template="adminInfo" />
<g:render template="messages" />
<div class="w-section">
	<div class="w-container body-text-container primary-small contains-admin-nav-bar">
		<h2>Permission List</h2>
		<g:if test="${params.objectId && params.objectName}">
			<h3>
				<g:link
						action="create"
						params="['objectId':params.objectId, 'selectedController':params.objectName]">
					Create a new permission for this object
				</g:link>
			</h3>
		</g:if>
		<div class="datagrid">
			<table>
				<thead>
				<tr>

					<th class="sortable sorted asc">ID</th>
					<th class="sortable sorted asc">Permission</th>
					<th class="sortable sorted asc">User</th>

				</tr>
				</thead>
				<tbody>
				<g:each in="${permissionInstanceList}" status="i" var="permissionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
							<g:link action="edit" id="${permissionInstance.id}">
								${fieldValue(bean: permissionInstance, field: "id")}
							</g:link>
						</td>

						<td>${fieldValue(bean: permissionInstance, field: "permissionsString")}</td>

						<td>
							<g:link controller="userAdmin" action="edit" id="${permissionInstance.user.id}">
								${permissionInstance.user}
							</g:link>
						</td>

					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
		<g:if test="${permissionInstanceTotal}">
			<div class="pagination">
				<g:paginate total="${permissionInstanceTotal}" />
			</div>
		</g:if>
	</div>
</div>
</body>

</html>
