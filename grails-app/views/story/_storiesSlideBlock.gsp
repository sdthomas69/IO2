<div class="w-slide">
    <div class="w-container">
        <div class="associated-stories-div-block">
            <g:each in="${story.stories.sort()}" var="feature" status="x">
                <g:if test="${x < counter + 3 && x >= counter}">
                    <div class="asbox fsbox">
                        <a class="associatedstoryflexlinkbox w-inline-block"
                           href="<blackFish:setLink object="${feature}" name="story"/>">
                            <blackFish:wflowChange
                                    sizes="(max-width: 767px) 96vw, (max-width: 991px) 222.671875px, 293.328125px"
                                    src="${resource(file: feature?.primaryImage?.smallSlideImage ?: thumbnail)}"
                                    small="${resource(file: feature?.primaryImage?.smallSlideImage ?: thumbnail)}"
                                    medium="${resource(file: feature?.primaryImage?.slideImage ?: thumbnail)}"
                                    large="${resource(file: feature?.primaryImage?.mediumSlideImage ?: thumbnail)}"
                                    width="100%"
                                    title="${feature?.primaryImage?.title}"
                                    alt="${feature?.primaryImage?.title}"/>
                            <div class="related-topics-div-block">
                                <h3 class="dctitle">${feature.pageTitle}</h3>

                                <p>${feature.shortDescription}</p>
                            </div>
                        </a>
                    </div>
                </g:if>
            </g:each>
        </div>
    </div>
</div>
