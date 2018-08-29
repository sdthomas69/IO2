<html>

<head>
    <link media="screen, projection" rel="stylesheet" type="text/css"
          href="<g:resource dir='css' file='normalize.css'/>"/>
    <link media="screen, projection" rel="stylesheet" type="text/css"
          href="<g:resource dir='css' file='foundation.css'/>"/>
    <link media="screen, projection" rel="stylesheet" type="text/css" href="<g:resource dir='css' file='style.css'/>"/>
    <script type="text/javascript" src="<g:resource dir='js' file='modernizr.js'/>"></script>
    <script type="text/javascript" src="<g:resource dir='js' file='vendor/jquery.js'/>"></script>

    <script>
        function getImageData(e) {

            var tgt = e.target || event.srcElement;

            //if( tgt.nodeName != 'IMG' )
            //return;

            var url = tgt.src;

            var alt = tgt.alt;

            var href = tgt.parentNode.href;

            this.onclick = null;

            //var dialog = window.opener.CKEDITOR.dialog.getCurrent();

            //if ( dialog.getName() == 'link' ) dialog.setValueOf('info', 'url', href);


            //window.opener.CKEDITOR.dialog.getCurrent().getContentElement('dialogTabId', 'dialogTabFieldId').setValue('yourValue');


            //window.opener.CKEDITOR.tools.callFunction(1, url);


            //var docIdField = dialog.getContentElement( 'info', 'docid' );
            //docIdField.setValue( docid );

            window.opener.CKEDITOR.tools.callFunction(1, url, function () {
                // Get the reference to a dialog window.
                var element,
                    dialog = this.getDialog();
                // Check if this is the Image dialog window.
                if (dialog.getName() == 'image') {
                    // Get the reference to a text field that holds the "alt" attribute.
                    element = dialog.getContentElement('info', 'txtAlt');
                    // Assign the new value.
                    if (element) element.setValue(alt);
                }
                if (dialog.getName() == 'link') {
                    // Get the reference to a text field that holds the "alt" attribute.
                    element = dialog.getContentElement('info', 'txtLink');
                    // Assign the new value.
                    if (element) element.setValue(href);
                    //[return false;]
                }
                // Return false to stop further execution - in such case CKEditor will ignore the second argument (fileUrl)
                // and the onSelect function assigned to a button that called the file browser (if defined).
                //[return false;]
            });

            window.close();
        }
    </script>

</head>

<body>
<div class="large-12 columns">
    <g:if test="${searchResult?.getTotalCount() > 0}">

        <ul class="small-block-grid-3 medium-block-grid-4 large-block-grid-6">
            <g:each var="result" in="${searchResult}" status="i">
                <g:link controller="file" action="title" params="['title': result.urlTitle]">
                    <img
                            onclick="getImageData(this)"
                            class="imageborder"
                            src="<g:resource file='${result.inBetweenImage}'/>"
                            alt="<g:resource file='${result.title}'/>"/>
                </g:link>
            </g:each>
        </ul>

        <g:if test="${searchResult?.getTotalCount() > params.max.toInteger()}">
            <div class="paginateButtons">
                <util:remotePaginate
                        action="imageSearch"
                        total="${searchResult?.getTotalCount()}"
                        update="list"
                        params="${params}"/>
            </div>
        </g:if>
    </g:if>
    <g:else>
        <p>There are no results for your query</p>
    </g:else>
</div>
<g:render template="/common/bodyScripts"/>
</body>

</html>

