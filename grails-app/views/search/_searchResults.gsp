<html>

<head>
    <meta name="layout" content="main"/>
    <title>Search Results</title>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small">
        <h1 class="story-title">Search</h1>

        <div class="w-form w-hidden-main w-hidden-medium peopleformsearchwrapper mobiletop">
            <g:render template="/search/sideSearch"/>
        </div>

        <div class="w-row">
            <!-- Left Column -->
            <div class="w-col w-col-9 w-clearfix description-text-column">
                <g:render template="remoteSearch"/>
            </div>
            <!-- Right Column -->
            <div class="w-col w-col-3 sidebar-right totop">
                <div class="w-form">
                    <div class="w-form w-hidden-small w-hidden-tiny peopleformsearchwrapper sidebaronly">
                        <g:render template="/search/sideSearch"/>
                    </div>
                </div>
                <g:include controller="tag" action="getTagCloud" params="[template: '/tag/tags']"/>
            </div>
        </div>
    </div>
</div>
</body>

</html>