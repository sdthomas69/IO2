<g:if test="${story.stories}">    <div class="associated-stories-section">        <div class="related-stories-slideshow w-slider" data-animation="slide" data-duration="500" data-hide-arrows="1"             data-infinite="1">            <div class="w-slider-mask">                <h3 class="centered eventstitle related-topics">Related topics</h3>                <g:set var="counter" value="${0}"/>                <g:each in="${story.stories.sort()}" var="thisStory" status="i">                    <g:if test="${counter % 3 == 0}">                        <g:render template="/story/storiesSlideBlock"/>                    </g:if>                    <g:set var="counter" value="${counter + 1}"/>                </g:each>            </div>            <div class="w-slider-arrow-left">                <div class="w-icon-slider-left"></div>            </div>            <div class="w-slider-arrow-right">                <div class="w-icon-slider-right"></div>            </div>            <div class="w-round w-shadow w-slider-nav"></div>        </div>    </div></g:if>