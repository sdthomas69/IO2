<%@ page import="cev.blackFish.User" %>

<html>
<head>
    <meta name="layout" content="main">
    <title>User List</title>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <g:render template="/userAdmin/adminInfo"/>
        <g:render template="/userAdmin/messages"/>
        <h2>List Users</h2>

        <div class="w-form">
            <g:form class="stories-form" action="list" method="get">
                <div class="w-row">
                    <div class="w-col w-col-3">
                        <select class="w-select select-field" id="bool" name="bool">
                            <option value="${params.bool}">${params.bool ? 'Sort Options: ' + params.bool : 'Sort Options'}</option>
                            <option value="">None</option>
                        </select>
                    </div>

                    <div class="w-col w-col-2">
                        <select class="w-select select-field" id="category" name="category">
                            <option value="${params.category}">${params.category ? 'Category: ' + params.category : 'Category'}</option>
                            <option value="">None</option>
                        </select>
                    </div>

                    <div class="w-col w-col-3">
                        <g:checkBox name="nameOnly" value="${false}"></g:checkBox>
                        <label for="nameOnly">Name Only</label>
                    </div>
                </div>

                <div class="w-row search-row">
                    <div class="w-col w-col-9 search-column admin">
                        <g:textField
                                class="w-input search-text-field"
                                id="search_query"
                                name="q" value="${params?.q?.encodeAsHTML()}"
                                placeholder="search"/>
                    </div>

                    <div class="w-col w-col-3 w-clearfix search-column admin">
                        <input class="w-button searchsubmit" type="submit" value="Search">
                    </div>
                </div>
            </g:form>
            <div class="datagrid">
                <table>
                    <thead>
                    <tr>

                        <g:sortableColumn property="firstName" title="First Name"/>

                        <g:sortableColumn property="lastName" title="Last Name"/>

                        <td>Email</td>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td>
                                <g:link action="edit" id="${userInstance.id}">
                                    ${fieldValue(bean: userInstance, field: "firstName")}
                                </g:link>
                            </td>
                            <td>
                                <g:link action="edit" id="${userInstance.id}">
                                    ${fieldValue(bean: userInstance, field: "lastName")}
                                </g:link>
                            </td>
                            <td>
                                <a href="${userInstance.email}">${userInstance.email}</a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <g:if test="${userInstanceList?.getTotalCount() > params?.max?.toInteger()}">
                <div class="paginateButtons">
                    <g:paginate total="${userInstanceList.getTotalCount()}" params="${params}"/>
                </div>
            </g:if>
        </div>
    </div>
</body>

</html>
