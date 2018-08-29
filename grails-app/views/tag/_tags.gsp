<g:if test="${tags}">
    <div class="relatedStoriesSideBG">
        <h4 class="subheader">Tags</h4>

        <p class="small">
            <blackFish:buildTagLink
                    template="/common/tagLink"
                    values="${tags}"
                    class="tag"
                    linkClass="tagLink"
                    controller="tag"
                    action="title"
                    minSize="8"
                    maxSize="8"/>
        </p>
    </div>
</g:if>
