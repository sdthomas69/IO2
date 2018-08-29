<html>
<head>
    <title>Change Password</title>
    <meta name="layout" content="mainCoe"/>
</head>

<body>
<div id="adminBg" class="row">
    <div class="large-12 columns">
        <g:render template="messages"/>
        <h2>Password Change Form</h2>
        <g:form method="post" action="updatePassword">
            <input type="hidden" name="id" value="${userInstance?.id}"/>
            <input type="hidden" name="version" value="${userInstance?.version}"/>
            <fieldset>
                <table>
                    <tbody>
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="password">
                                <strong>New Password</strong>:<br/>
                                Must contain at least 7 characters, <br/>
                                at least one number and include one<br/>
                                of these characters ! @ # $ % ^ &
                            </label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
                            <input type="password" name="password" id="password"
                                   value="${fieldValue(bean: userInstance, field: 'password')}"/>
                        </td>
                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="confirm"><strong>Confirm Password Change</strong>:</label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'confirm', 'errors')}">
                            <input type="password" name="confirm" id="confirm"
                                   value="${fieldValue(bean: userInstance, field: 'confirm')}"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <g:actionSubmit class="button" value="Update" action="updatePassword"/>
            </fieldset>
        </g:form>
    </div>
</div>
</body>

</html>
