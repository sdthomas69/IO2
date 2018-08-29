<%@ page import="cev.blackFish.Tag" %>

<div class="w-row">
    <div class="w-col w-col-6">
        <label for="name">Title:</label>
        <g:textField
                name="title"
                required=""
                class="w-input admin-text-field value ${hasErrors(bean: tagInstance, field: 'title', 'errors')}"
                value="${tagInstance?.title}"/>
    </div>
</div>

