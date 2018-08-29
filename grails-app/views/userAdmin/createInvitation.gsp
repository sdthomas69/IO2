<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Create Email</title>
    <script type="text/javascript" src="<g:resource dir='js' file='ckeditor/ckeditor.js'/>"></script>
    <script>
        function validate() {

            var email = emailForm.from.value;
            var email2 = emailForm.tAddress.value;

            if (email == '') {
                alert('Please enter a valid From email address.');
                event.returnValue = false;
            }
            if (!validateEmail(email)) {
                alert('Invalid Email From Address');
                event.returnValue = false;
            }
            if (email2 == '') {
                alert('Please enter a valid To email address.');
                event.returnValue = false;
            }
            if (!validateEmail(email2)) {
                alert('Invalid Email To Address');
                event.returnValue = false;
            }
        }

        function validateEmail(sEmail) {

            var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

            if (filter.test(sEmail)) {
                return true;
            }
            else {
                return false;
            }
        }
    </script>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small contains-admin-nav-bar">
        <g:render template="adminInfo"/>
        <h1>Send Invitation</h1>
        <g:render template="errors"/>
        <g:form method="post" id="emailForm" name="emailForm" onsubmit="validate();">
            <div class="w-row">
                <div class="w-col w-col-12">
                    <label for="name">Subject:</label>
                    <g:textField
                            name="subject"
                            required=""
                            class="w-input admin-text-field value"
                            value="${params.subject}"/>
                    <label for="from">From Address:</label>
                    <g:textField
                            name="from"
                            required=""
                            class="w-input admin-text-field value"
                            value="${currentUser?.email}"/>
                    <label for="tAddress">To Address:</label>
                    <g:textField
                            name="tAddress"
                            required=""
                            class="w-input admin-text-field value"
                            value="${params.tAddress}"/>
                    <label for="name">Body:</label>
                    <textarea class="ckeditor" id="emailText" name="emailText">
                        ${params.emailText}
                    </textarea>
                </div>
            </div>
            <g:actionSubmit class="w-button submit-button" id="btnValidate" action="sendInvitation" value="Send"/>
        </g:form>
    </div>
</div>

</body>
</html>
