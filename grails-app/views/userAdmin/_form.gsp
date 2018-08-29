<%@ page import="cev.blackFish.User" %>

<div class="w-row">
    <div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">User Name</label>
        <input type="text" id="username" name="username" class="w-input"
               value="${fieldValue(bean: userInstance, field: 'username')}"/>
    </div>
    <%--<div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">Full Name</label>
        <input type="text" id="name" name="name" class="w-input" value="${fieldValue(bean:userInstance,field:'name')}"/>
    </div>--%>
</div>

<div class="w-row">
    <div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">First Name</label>
        <input type="text" id="firstName" name="firstName" class="w-input"
               value="${fieldValue(bean: userInstance, field: 'firstName')}"/>
    </div>

    <div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">Last Name</label>
        <input type="text" id="lastName" name="lastName" class="w-input"
               value="${fieldValue(bean: userInstance, field: 'lastName')}"/>
    </div>
</div>

<div class="w-row">
    <div class="w-col w-col-4 field-col">
        <label for="field" class="field-label">Web Site</label>
        <input type="text" id="url" name="url" class="w-input" value="${fieldValue(bean: userInstance, field: 'url')}"/>
    </div>

    <div class="w-col w-col-4 field-col">
        <label for="field" class="field-label">Email Address</label>
        <input type="text" id="email" name="email" class="w-input"
               value="${fieldValue(bean: userInstance, field: 'email')}"/>
    </div>

    <div class="w-col w-col-4 field-col">
        <label for="field" class="field-label">Department</label>
        <input type="text" id="department" name="department" class="w-input"
               value="${fieldValue(bean: userInstance, field: 'department')}"/>
    </div>
</div>

<div class="w-row">
    <div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">Password</label>
        <input
                type="password"
                name="password"
                id="password"
                class="w-input admin-text-field value ${hasErrors(bean: userInstance, field: 'password', 'errors')} required"
                value="${fieldValue(bean: userInstance, field: 'password')}"/>
    </div>

    <div class="w-col w-col-6 field-col">
        <label for="field" class="field-label">Re-type Password</label>
        <input
                type="password"
                name="confirm"
                id="password"
                class="w-input admin-text-field value ${hasErrors(bean: userInstance, field: 'confirm', 'errors')} required"
                value="${fieldValue(bean: userInstance, field: 'confirm')}"/>
    </div>
</div>

<div class="w-row">
    <shiro:hasRole name="Administrator">
        <div class="w-col w-col-4 field-col">
            <div class="w-checkbox">
                <g:checkBox class="w-checkbox-input" name="confirmed" value="${userInstance.confirmed}"></g:checkBox>
                <label class="w-form-label" for="confirmed">
                    Confirmed
                </label>
            </div>
        </div>
    </shiro:hasRole>
</div>

<div class="spacerdiv"></div>
<label for="field" class="field-label">A little about yourself:</label>
<textarea placeholder="Description" id="description" name="description" class="w-input">
    ${userInstance.description}
</textarea>

<div class="spacerdiv"></div>

<div class="w-row">
    <div class="w-col w-col-6">
        <label for="position" class="field-label">Position</label>
        <g:set var="category" value="${userInstance?.position}"/>
        <g:render template="/userAdmin/subcategories"/>
        <div class="spacerdiv"></div>
    </div>
    <shiro:hasRole name="Administrator">
        <div class="w-col w-col-6">
            <label for="date" class="field-label">Date</label>
            <g:datePicker
                    name="date"
                    precision="day"
                    years="${2010..2020}"
                    value="${userInstance?.date}"/>
            <div class="spacerdiv"></div>
        </div>
    </shiro:hasRole>
    <g:if test="${userInstance.id}">
        <div class="w-col w-col-6">
            <shiro:hasRole name="Administrator">
                <label for="Category" class="field-label">Roles</label>

                <div class="spacerdiv"></div>
                <g:select
                        class="w-select"
                        name="roles"
                        from="${cev.blackFish.Role.list(['sort': 'name'])}"
                        multiple="yes"
                        optionKey="id"
                        size="5"
                        value="${userInstance?.roles*.id}"/>
            </shiro:hasRole>
        </div>
    </g:if>
</div>





