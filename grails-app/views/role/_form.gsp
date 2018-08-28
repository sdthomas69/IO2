<%@ page import="cev.blackFish.Role" %>
<div class="w-row">
	<div class="w-col w-col-8">
		<label for="name">Name:</label>
		<g:textField
			name="name"
			required=""
			class="w-input admin-text-field value ${hasErrors(bean:roleInstance, field:'name','errors')}"
			value="${roleInstance?.name}"
		/>
	</div>
	<g:if test="${roleInstance.id}">
		<div class="w-col w-col-8">
			<label for="permissions">Existing Permissions:</label>
			<g:if test="${roleInstance.permissions}">
				<g:each in="${roleInstance.permissions}" var="perm">
					<div class="w-checkbox">
						Remove  <g:checkBox name="permissionsToRemove" value="${perm}" checked="${false}" />
					</div>
					<g:textField
						name="permissions"
						class="w-input admin-text-field value ${hasErrors(bean:roleInstance, field:'permissions','errors')}"
						value="${perm}"
					/>
				</g:each>
			</g:if>
			<g:else>
				<g:textField
					name="permissions"
					class="w-input admin-text-field value ${hasErrors(bean:roleInstance, field:'permissions','errors')}"
					value=""
				/>
			</g:else>
			<label for="newPermission">Add a New Permission:</label>
			<g:textField
				name="newPermission"
				class="w-input admin-text-field value"
				value=""
			/>
		</div>
		<div class="w-col w-col-8">
			<label for="name">Users:</label>
			<g:if test="${roleInstance?.users}">
				<ul>
					<g:each in="${roleInstance.users.sort({it.lastName})}" var="u">
						<li>
							<g:link controller="userAdmin" action="edit" id="${u.id}">${u?.encodeAsHTML()}</g:link>
						</li>
					</g:each>
				</ul>
			</g:if>
		</div>
	</g:if>
</div>

