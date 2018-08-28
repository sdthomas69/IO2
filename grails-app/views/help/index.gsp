
<%@ page import="cev.blackFish.Help" %>

<html>
	<head>
		<meta name="layout" content="main" />
		<title>Help List</title>
	</head>
	<body>
		<div class="w-section">
			<div class="w-container body-text-container primary-small contains-admin-nav-bar">
				<div class="large-12 columns">
					<g:render template="adminInfo" />
					<g:if test="${flash.message}">
						<div class="message" role="status">${flash.message}</div>
					</g:if>
					<div class="datagrid">
						<table>
							<thead>
								<tr>

									<g:sortableColumn property="title" title="${message(code: 'help.selecteController.label', default: 'Title')}" />

									<g:sortableColumn property="selectedController" title="${message(code: 'help.selecteController.label', default: 'Controller')}" />

									<g:sortableColumn property="activity" title="${message(code: 'help.activity.label', default: 'Action')}" />

								</tr>
							</thead>
							<tbody>
							<g:each in="${helpInstanceList}" status="i" var="helpInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td><g:link action="edit" id="${helpInstance.id}">${fieldValue(bean: helpInstance, field: "title")}</g:link></td>

									<td><g:link action="edit" id="${helpInstance.id}">${fieldValue(bean: helpInstance, field: "selectedController")}</g:link></td>

									<td>${fieldValue(bean: helpInstance, field: "activity")}</td>

								</tr>
							</g:each>
							</tbody>
						</table>
					</div>
					<div class="pagination">
						<g:paginate total="${helpInstanceCount ?: 0}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
