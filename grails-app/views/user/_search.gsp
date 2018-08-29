<div class="peopleformsearchwrapper">
    <div class="w-form">
        <g:form class="w-clearfix" url='[controller: "user", action: "people"]' id="search-form" name="search-form"
                method="get">
            <g:textField class="w-input peoplesearchfield" id="search_query" name="q"
                         value="${params.q.encodeAsHTML()}"/>
            <input class="w-button peoplesearchbutton" type="submit" value="Search"/>
        </g:form>
    </div>
</div>