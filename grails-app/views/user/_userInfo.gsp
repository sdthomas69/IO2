<g:set var="icon" value="${grailsApplication.config.genericPerson}"/>

<div class="Peep clearfix">

    <div class="smaller-primary-image people-image small-12 medium-4 large-3 columns">
        <img class="size-medium" src="<g:resource file='${user?.primaryImage?.smallImageSquared ?: icon}'/>"
             alt="${user}"/>
        <g:render template="editButton"/>
    </div>

    <div class="small-12 medium-8 large-9 columns">
        <section class="article__content">
            <h1>${user}</h1>

            <h3>${user.position} | <span>${user.department}</span></h3>
            <ul class="w-list-unstyled peopleinfo-list">
                <g:if test="${user.email}">
                    <li class="people-info-list-item email">
                        <a href="mailto:${user.email}">
                            ${user.email}
                        </a>
                    </li>
                </g:if>
                <g:if test="${user.url}">
                    <li class="people-info-list-item weblink">
                        <a href="${user.url}">Website</a>
                    </li>
                </g:if>
            </ul>
            ${user.description}
        </section>
    </div>
</div>


