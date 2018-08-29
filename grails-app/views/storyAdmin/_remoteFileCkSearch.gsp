<html>

<head>
    <%--<style>
        html,
        body {padding:0; margin:0; background:black; }
        table {width:100%; border-spacing:15px; }
        td {text-align:center; padding:5px; background:#181818; }
        img {border:5px solid #303030; padding:0; verticle-align: middle;}
        img:hover { border-color:blue; cursor:pointer; }
    </style>
    --%>
    <link media="screen, projection" rel="stylesheet" type="text/css"
          href="<g:resource dir='css' file='normalize.css'/>"/>
    <link media="screen, projection" rel="stylesheet" type="text/css"
          href="<g:resource dir='css' file='foundation.css'/>"/>
    <script type="text/javascript" src="<g:resource dir='js' file='vendor/custom.modernizr.js'/>"></script>
    <script type="text/javascript" src="<g:resource dir='js' file='vendor/jquery.js'/>"></script>
    <g:javascript plugin="remote-pagination" library="remoteNonStopPageScroll"/>
    <script>
        function getImageData(e) {

            var tgt = e.target || event.srcElement;

            var url = tgt.src;

            var alt = tgt.alt;

            var href = tgt.parentNode.href;

            var funcNum = getUrlParam('CKEditorFuncNum');

            this.onclick = null;

            window.opener.CKEDITOR.tools.callFunction(funcNum, url);

            <%--window.opener.CKEDITOR.tools.callFunction( funcNum, url, function() {
                // Get the reference to a dialog window.
                var element;
                var dialog = this.getDialog();

                // Check if this is the Image dialog window.
                if ( dialog.getName() == 'image' ) {
                    // Get the reference to a text field that holds the "alt" attribute.
                    element = dialog.getContentElement( 'info', 'txtAlt' );

                    // Assign the new value.
                    if ( element ) element.setValue("alt text");
                    //alert(element.getValue());
                }
                if ( dialog.getName() == 'link' ) {

                    element = dialog.getContentElement( 'info', 'txtLink' );

                    if ( element ) element.setValue("alt text");
                }
                // Return false to stop further execution - in such case CKEditor will ignore the second argument (fileUrl)
                // and the onSelect function assigned to a button that called the file browser (if defined).
                //[return false;]
            });--%>

            window.close();
        }

        function getUrlParam(paramName) {

            var reParam = new RegExp('(?:[\?&]|&amp;)' + paramName + '=([^&]+)', 'i');
            var match = window.location.search.match(reParam);

            return (match && match.length > 1) ? match[1] : '';
        }

    </script>

</head>

<body>
<div id="list" class="large-12 columns">
    <g:render template="remoteCk"/>
</div>
</body>

</html>

