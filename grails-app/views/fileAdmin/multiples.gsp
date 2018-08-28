<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="oldMain" />
        <title>Upload Files</title>
    </head>
    <body>
        <body class="story">
        	<div id="adminBg" class="row">
				<div class="large-12 columns">
					<h1>Upload Files</h1>
	            	<g:uploadForm method="post" action="multiple">
	                	<input type="hidden" name="id" value="${params?.id}" />
	                    <table>
	                        <tbody>
                                <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="title">Mobile Movie:</label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean:file,field:'title','errors')}">
	                                    <input type="file" name="phoneMovie"/>
	                                </td>
	                            </tr>
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="title">Poster Image:</label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean:file,field:'title','errors')}">
	                                    <input type="file" name="posterImage"/>
	                                </td>
	                            </tr>
	                         </tbody>
	                    </table>

					<div class="buttons">
	                    <input class="button" type="submit" value="Create" />
	                </div>
           		</g:uploadForm>
           	</div>
        </div>
    </body>
</html>