<div class="w-row">
	<div class="w-col w-col-6">
		<label for="name">Permission:</label>
		<g:textField 
			name="permissionsString" 
			required="" 
			class="w-input admin-text-field value ${hasErrors(bean:menuSetInstance, field:'permissionsString','errors')}"
			value="${permissionInstance?.permissionsString}"
		/>
		
		<label for="name">Actions: ${permissionInstance?.actions()}</label>
		<g:select 
			id="actions" 
			name="actions" 
			from="${actions}" 
			multiple="yes"
			size="10"
			value="${permissionInstance?.actions()}" 
			class="w-select"
			noSelection="['*':'All Actions']" 
		/>
		
		<label for="name">Controller:</label>
		<g:select 
			id="selectedController" 
			name="selectedController" 
			from="${grailsApplication.controllerClasses}" 
			optionKey="logicalPropertyName" 
			required="" 
			value="${permissionInstance?.controller()}" 
			class="w-select"
		/>
		
		<label for="name">User:</label>
		<g:select 
			id="user" 
			name="user.id" 
			from="${cev.blackFish.User.list([sort:'lastName'])}" 
			optionKey="id" 
			required="" 
			value="${permissionInstance?.user?.id}" 
			class="w-select"
		/>
	</div>
</div>

