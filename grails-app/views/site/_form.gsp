<%@ page import="cev.blackFish.Site" %>

<div class="w-row">
	<div class="w-col w-col-6">
	
		<label for="name">Title:</label>
		<g:textField 
			name="title" 
			required="" 
			class="w-input admin-text-field value ${hasErrors(bean:siteInstance, field:'title','errors')}"
			value="${siteInstance?.title}"
		/>
		
		<label for="name">Style Sheet:</label>
		<g:textField 
			name="styleSheet" 
			required="" 
			class="w-input admin-text-field value ${hasErrors(bean:siteInstance, field:'styleSheet','errors')}"
			value="${siteInstance?.styleSheet}"
		/>
		
		<%--<label for="name">Portal:</label>
		<g:select 
			id="portal" 
			name="portal.id" 
			from="${cev.blackFish.Story.list()}" 
			optionKey="id" 
			value="${siteInstance?.portal?.id}" 
			class="many-to-one" 
			noSelection="['null': '']"
		/>--%>
		
		<label for="name">Main Menu:</label>
		<g:select 
			id="main" 
			name="main.id" 
			from="${cev.blackFish.MenuSet.list()}" 
			optionKey="id" 
			required="" 
			value="${siteInstance?.main?.id}" 
			class="many-to-one"
		/>
		
		<label for="name">Auxiliary Menu:</label>
		<g:select 
			id="main" 
			name="main.id" 
			from="${cev.blackFish.MenuSet.list()}" 
			optionKey="id" 
			required="" 
			value="${siteInstance?.main?.id}" 
			class="many-to-one"
		/>

	</div>
</div>

