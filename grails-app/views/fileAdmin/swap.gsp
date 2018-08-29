<html>
<head>
    <meta name="layout" content="main"/>
    <title>Change File</title>
    <script type=text/javascript>

        function validate() {

            var file = upload.mfile.value;

            if (file == '') {
                alert('You must select a file.');
                event.returnValue = false;
            }
        }
    </script>
</head>

<body>
<g:render template="/fileAdmin/adminInfo"/>
<g:render template="/fileAdmin/messages"/>
<div class="w-section">
    <div class="w-container body-text-container single-column admin-column">
        <div class="w-form">
            <h1>Change File</h1>
            <g:uploadForm action="changePath" method="post" name="upload" onsubmit="validate();">
                <input type="hidden" name="id" value="${params?.id}"/>
                <input type="hidden" name="version" value="${file?.version}"/>
                <fieldset>
                    <label for="type">File Type:</label>
                    <g:select
                            name="type"
                            class="w-select"
                            value="${file.type}"
                            from="${['Image', 'Text/Doc', 'HTML', 'PowerPoint', 'Quicktime', 'Keynote']}"
                            noSelection="['': '-Select a file type-']"/>
                    <label for="mfile">File:</label>
                    <input type="file" id="mfile" name="mfile" size="40"/>
                </fieldset>
                <input class="w-button submit-button" type="submit" value="Upload"/>
            </g:uploadForm>
        </div>
    </div>
</div>
</body>
</html>
