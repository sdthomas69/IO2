<%@ page import="cev.blackFish.MenuSet" %>

<div class="w-row">
	<div class="w-col w-col-6">
		<label for="name">Title:</label>
		<g:textField 
			name="title" 
			maxlength="100" 
			required="" 
			class="w-input admin-text-field value ${hasErrors(bean:menuSetInstance, field:'title','errors')}"
			value="${menuSetInstance?.title}"
		/>
		<label for="name">Date:</label>
		<g:datePicker name="date" precision="day" value="${menuSetInstance?.date}"  />
	</div>
</div>
<g:if test="${menuSetInstance.id}">
	<div class="w-row">
		<div class="w-col w-col-6">
			<label for="name">Stories:</label>
			<ul class="one-to-many">
				<g:each in="${menuSetInstance?.children?}" var="c">
				    <li>
				    	<g:link controller="story" action="show" id="${c.id}">
				    		${c?.encodeAsHTML()}
				    	</g:link> 
				    	<g:link 
				    		class="w-button submit-button" 
				    		action="removeStory" 
				    		params="['id':menuSetInstance.id, 'story.id':c.id]">
				    		Remove
				    	</g:link>
				    </li>
				</g:each>
			</ul>
			<%--<g:link 
				class="w-button submit-button" 
				controller="storyAdmin" 
				action="createText" 
				params="['menuSet.id': menuSetInstance?.id]">
				New Story
			</g:link>--%>
		</div>
	</div>
</g:if>


