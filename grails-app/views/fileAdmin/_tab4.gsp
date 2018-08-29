<div class="w-row">
    <div class="w-col w-col-12">
        <g:if test="${file.stories}">
            <div class="w-clearfix associated-stories">
                <h4 class="h4-heading-admin">Associated Stories</h4>
                <g:render template="storyList"/>
            </div>
        </g:if>
    </div>
</div>