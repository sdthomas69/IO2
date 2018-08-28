<%@ page import="cev.blackFish.Help" %>

<div class="w-row">
	<div class="w-col w-col-12">
	
		<label for="name">Title:</label>
		<g:textField 
			name="title" 
			required="" 
			class="w-input admin-text-field value ${hasErrors(bean:helpInstance, field:'title','errors')}"
			value="${helpInstance?.title}"
		/>
		
		<label for="name">Controller:</label>
		<g:select 
			id="selectedController" 
			name="selectedController" 
			from="${grailsApplication.controllerClasses}" 
			optionKey="logicalPropertyName" 
			required="" value="${helpInstance.selectedController}" class="many-to-one"
		/>
		
		<label for="name">Action:</label>
		<g:select 
			id="activity" 
			name="activity" 
			from="${actions}" 
			optionKey="" 
			value="${helpInstance.activity}" class="many-to-one"
		/>

		<g:if test="${helpInstance.id}">
		
			<label for="name">Description:</label>
			<textarea class="ckeditor" id="description" name="description">
				${helpInstance.description}
			</textarea>
			
			<g:if test="${helpInstance.description}">
			
				<label for="name">Primary Image:</label>
				
				<g:if test="${helpInstance.primaryImage}">
					<div class="col-filePrimary">
						<g:link controller='file' action='show' id='${helpInstance.primaryImage.id}'>
							<img class="imageborder" src="<g:resource file='${helpInstance.primaryImage.smallImage}' />" alt="${helpInstance.primaryImage}" border="0"/>
							<p><g:link params='["id":helpInstance.id]' action='unSetPrimaryImage'>(- Remove)</g:link></p>
						</g:link>
					</div>
				</g:if>
				<g:if test="${!helpInstance.primaryImage}">
					<g:link class="button small success" 
						params="['helpId':helpInstance.id]" 
						controller="fileAdmin" 
						action="create">
						Add a New File
					</g:link>
				</g:if>
			
			</g:if>

		</g:if>
	</div>
</div>

