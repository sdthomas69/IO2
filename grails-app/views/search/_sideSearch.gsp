<div class="peopleformsearchwrapper">
    <div class="w-form">
        <g:form url='[controller: "search", action: "index"]' id="search-form" name="search-form" method="get">
            <g:textField class="w-input peoplesearchfield" id="search_query" name="q"
                         value="${params?.q?.encodeAsHTML()}"/>
            <input class="w-button peoplesearchbutton" type="submit" value="Search"/>
        </g:form>
    </div>
</div>