<html>

<head>
    <meta name="layout" content="main">
    <title>People</title>
    <g:render template="/common/masonJs"/>
    <style>

    .w-inline-block grid-item {
        -moz-column-count: 6;
        -moz-column-gap: 0px;
        -webkit-column-count: 6;
        -webkit-column-gap: 0px;
        column-count: 6;
        column-gap: 0px;
    }

    @media (max-width: 991px) {

        .w-inline-block grid-item {
            -moz-column-count: 3;
            -moz-column-gap: 0px;
            -webkit-column-count: 3;
            -webkit-column-gap: 0px;
            column-count: 3;
            column-gap: 0px;
        }
    }

    @media (max-width: 766px) {

        .w-inline-block grid-item {
            -moz-column-count: 2;
            -moz-column-gap: 0px;
            -webkit-column-count: 2;
            -webkit-column-gap: 0px;
            column-count: 2;
            column-gap: 0px;
        }
    }

    @media (max-width: 480px) {

        .w-inline-block grid-item {
            -moz-column-count: 1;
            -moz-column-gap: 0px;
            -webkit-column-count: 1;
            -webkit-column-gap: 0px;
            column-count: 1;
            column-gap: 0px;
        }
    }

    </style>
</head>

<body>
<div class="w-section">
    <div class="w-container body-text-container primary-small">
        <h1 class="story-title">People</h1>

        <div class="w-form w-hidden-main w-hidden-medium peopleformsearchwrapper mobiletop">
            <g:form
                    data-name="people-search"
                    url='[controller: "user", action: "people"]'
                    id="wf-form-people-search"
                    name="wf-form-people-search"
                    method="get"
                    class="w-clearfix">
                <g:textField class="w-input peoplesearchfield" id="search_query" name="q"
                             value="${params.q.encodeAsHTML()}"/>
                <input class="w-button peoplesearchbutton" type="submit" value="Search"/>
            </g:form>
        </div>

        <div class="w-row">

            <!-- Left Column -->

            <div class="w-col w-col-9 w-clearfix description-text-column">
                <div id="peopleList">
                    <g:render template="remotePeopleList"/>
                </div>
            </div>

            <!-- Right Column -->

            <div class="w-col w-col-3 sidebar-right totop">
                <div class="w-form">
                    <div class="w-form w-hidden-small w-hidden-tiny peopleformsearchwrapper sidebaronly">
                        <g:form
                                data-name="people-search"
                                url='[controller: "user", action: "people"]'
                                id="wf-form-people-search"
                                name="wf-form-people-search"
                                method="get">
                            <g:textField class="w-input peoplesearchfield" id="search_query" name="q"
                                         value="${params.q.encodeAsHTML()}"/>
                            <input class="w-button peoplesearchbutton" type="submit" value="Search"/>
                        </g:form>
                    </div>
                </div>

                <div class="w-nav sidebar-nav hidden" data-collapse="none" data-animation="default" data-duration="400"
                     data-contain="1">
                    <a class="w-button sidebar-dropdown-button" href="#"
                       data-ix="sidebar-rollover-category">Category</a>
                    <nav class="w-nav-menu w-clearfix sidebar-menu-nav-category" role="navigation"
                         data-ix="sidebar-onload-hide">
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['category': 'Faculty']">Faculty</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['category': 'Graduate Student']">Graduate Students</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['category': 'Undergraduate Student']">Undergraduate Students</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['category': 'Staff']">Staff</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people">All</g:link>
                    </nav>
                    <a class="w-button sidebar-dropdown-button" href="#"
                       data-ix="sidebar-rollover-research">Research</a>
                    <nav class="w-nav-menu w-clearfix sidebar-menu-nav-research" role="navigation"
                         data-ix="sidebar-onload-hide">
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['option': 'Biological Oceanography']">Biological</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['option': 'Chemical Oceanography']">Chemical</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['option': 'MG&G']">Marine Geology & Geophysics</g:link>
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['option': 'Physical Oceanography']">Physical</g:link>
                    </nav>
                    <shiro:hasRole name="Administrator">
                        <a class="w-button sidebar-dropdown-button" href="#"
                           data-ix="sidebar-rollover-admin">Admin Options</a>
                        <nav class="w-nav-menu w-clearfix sidebar-menu-nav-admin" role="navigation"
                             data-ix="sidebar-onload-hide">
                            <g:link class="w-nav-link sidebar-nav-link" action="people"
                                    params="['cv': 'true']">Has CV</g:link>
                            <g:link class="w-nav-link sidebar-nav-link" action="people"
                                    params="['review': 'true']">Has Activity Report</g:link>
                            <g:link class="w-nav-link sidebar-nav-link" action="people"
                                    params="['evaluation': 'true']">Has Self Evaluation</g:link>
                        </nav>
                    </shiro:hasRole>
                    <a class="w-button sidebar-dropdown-button" href="#"
                       data-ix="sidebar-rollover-download">Download Options</a>
                    <nav class="w-nav-menu w-clearfix sidebar-menu-nav-download" role="navigation"
                         data-ix="sidebar-onload-hide">
                        <g:link class="w-nav-link sidebar-nav-link" action="people"
                                params="['download': 'all', 'format': 'excel']">Excel</g:link>
                        <%--								<g:link class="w-nav-link sidebar-nav-link" action="people" params="['download':'all', 'format':'pdf']">PDF</g:link>--%>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var container = document.querySelector('.grid');
    var msnry = new Masonry(container, {
        // options
        itemSelector: '.grid-item',
        gutter: 0
    });

    var ias = $.ias({
        container: ".grid",
        item: ".grid-item",
        pagination: "#page-nav",
        next: ".nextLink",
        negativeMargin: 10,
        delay: 100
    });

    ias.on('render', function (items) {
        $(items).css({opacity: 0});
    });

    ias.on('rendered', function (items) {
        msnry.appended(items);
        msnry.layout();
    });

    ias.extension(new IASSpinnerExtension());
    //ias.extension(new IASTriggerExtension({offset: 3}));
    ias.extension(new IASNoneLeftExtension({text: 'There are no more pages left to load.'}));
</script>
</body>

</html>
