<div class="w-row">
    <div class="w-col w-col-12">
        <g:if test="${tagInstance.users}">
            <div class="w-clearfix associated-stories">
                <h4 class="h4-heading-admin">Tagged People</h4>
                <g:render template="userList"/>
            </div>
        </g:if>
    </div>
</div>

<div class="w-form">
    <g:formRemote
            url="[
                    controller: 'tagAdmin',
                    action    : 'userSearch',
                    params    : ['id': tagInstance.id, sort: 'lastName', order: 'asc', 'tab': 'users']
            ]"
            update="[success: 'list2', failure: 'error2']"
            name="remotePersonForm"
            id="${tagInstance.id}"
            method="get">
        <div class="w-row search-row">
            <div class="w-col w-col-9 search-column admin">
                <g:textField class="w-input search-text-field files-field" id="search_query2" name="q"
                             value="${params.q}"/>
            </div>

            <div class="w-col w-col-3 w-clearfix search-column admin">
                <input class="w-button searchsubmit" type="submit" value="Search"/>
            </div>
        </div>
    <%--<div class="w-col w-col-2">
        <select class="w-select select-field" name="category" size="1">
            <option value="">Category</option>
            <option value="Faculty">Faculty</option>
            <option value="Staff">Staff</option>
            <option value="Graduate Student">Graduate Student</option>
        </select>
    </div>--%>
    </g:formRemote>
    <g:form method="post">
        <input type="hidden" name="id" value="${tagInstance.id}"/>
        <input type="hidden" name="version" value="${tagInstance?.version}"/>

        <div id="list2"></div>
    </g:form>
    <div id="error2"></div>
</div>