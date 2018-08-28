<html>
	<head>
		<meta name="layout" content="main" />
		<title>Tags</title>
	</head>

	<body>
		<g:render template="/tagAdmin/adminInfo" />
		<div class="w-section">
			<div class="w-container body-text-container single-column admin-column">
				<div class="w-form">
					<g:form class="stories-form" action="list" method="get">
						<div class="w-row search-row">
							<div class="w-col w-col-9 search-column admin">
								<g:textField 
									class="w-input search-text-field" 
									id="search_query"
									name="q" value="${params?.q?.encodeAsHTML()}"
									placeholder="search" 
								/>
							</div>
							<div class="w-col w-col-3 w-clearfix search-column admin">
								<input class="w-button searchsubmit" type="submit" value="Search">
							</div>
						</div>
					</g:form>
				</div>
				<h2>Tag List</h2>
				<div class="datagrid">
					<table>
						<thead>
							<tr>
								<td>Title</td>
							</tr>
						</thead>
						<tbody>
							<g:each in="${tagInstanceList}" status="i" var="tagInstance">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
									<td>
										<g:link controller="tag" action="show" id="${tagInstance.id}">
											${fieldValue(bean:tagInstance, field:'title')}
										</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
				<g:if test="${tagInstanceList?.getTotalCount() > params?.max?.toInteger()}">
		        	<div class="paginateButtons">
		                <g:paginate total="${tagInstanceList.getTotalCount()}" params="${params}" />
		            </div>
				</g:if>
			</div>
		</div>

	</body>

</html>
