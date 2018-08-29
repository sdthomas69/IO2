<g:if test="${user?.userStories*.published?.contains(true)}">
    <g:set var="thumbnail" value="${grailsApplication.config.default_thumbnail}"/>
    <div>
        <h3 class="eventstitle">ASSOCIATED&nbsp;STORIES</h3>
        <ul class="w-clearfix latest-news-list">
            <g:each in="${user.userStories.sort()}" var="story">
                <g:if test="${story.published}">
                    <li class="latest-news-item">
                        <a class="w-inline-block" href="<blackFish:setLink object="${story}" name="story"/>">
                            <div class="w-row">
                                <div class="w-col w-col-3 w-col-small-3 w-col-tiny-3">
                                    <img class="congratsimage latest-news-image"
                                         src="<g:resource file='${story?.primaryImage?.tinyImage ?: thumbnail}'/>"
                                         width="64">
                                </div>

                                <div class="w-col w-col-9 w-col-small-9 w-col-tiny-9">
                                    <h4 class="latest-news-heading">${story.title}</h4>
                                <%--<div class="latest-news-text">
                                    <blackFish:shortenText text="${story.shortDescription}" size="50" /> ...
                                </div>
                            --%></div>
                            </div>
                        </a>
                    </li>
                </g:if>
            </g:each>
        </ul>
    </div>
</g:if>