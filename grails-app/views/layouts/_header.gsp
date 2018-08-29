<g:set var="menuTitle" value="${site?.main?.title ? site.main.title : 'Main'}"/>
<shiro:isLoggedIn>
    <g:set var="link" value="${createLink(uri: '/')}"/>
</shiro:isLoggedIn>
<shiro:isNotLoggedIn>
    <g:set var="link" value="${createLink(uri: '/index.html')}"/>
</shiro:isNotLoggedIn>

<%-- Top Bar --%>
<div class="utilities-section w-hidden-small w-hidden-tiny" id="Top">
    <div class="w-container">
        <div class="left utilities-container w-clearfix">
            <a class="uwhome w-inline-block" href="http://www.washington.edu" target="_blank">
                <img src="<g:resource dir='images' file='uw-w-logo.png'/>" width="32">
            </a>
            <a class="coenv uwhome w-inline-block" href="https://environment.uw.edu" target="_blank">
                <img src="<g:resource dir='images' file='coenv-white.png'/>" width="201">
            </a>
        </div>

        <div class="right utilities-container">
            <%--			<g:link class="member-portal utilities-text-link" uri="/story/Contact">Contact</g:link>--%>
            <g:include
                    controller="menuSet"
                    action="getMenuSetByTitle"
                    params="[template: '/common/topUtil', 'title': 'Utilities']"/>
            <a class="modal-link w-inline-block" href="#">
                <img class="modal-link searchbutton" src="<g:resource dir='images' file='search-icon-white.png'/>"
                     width="27" search="Search">
            </a>
            <a class="modal-link searchme utilities-text-link" href="#">search</a>
        </div>
    </div>
</div>

<%-- Main Menu --%>
<div class="navigationbar w-nav" data-animation="over-right" data-collapse="medium" data-contain="1" data-duration="400"
     data-ix="display-nav">
    <div class="w-container">
        <a class="w-clearfix w-nav-brand" href="${link}">
            <h1 class="eewh1">Interactive Oceans</h1>
            <img src="<g:resource dir='images' file='eew-logo.png'/>" class="soo-logo-image soo-logo-small" width="75">
        </a>
        <nav class="navmenu w-nav-menu" role="navigation">
            <g:include
                    controller="menuSet"
                    class="navlink w-nav-link"
                    action="getMenuSetByTitle"
                    params="[template: '/common/newMenu', 'title': menuTitle]"/>
            <div class="linediv w-hidden-main"></div>
            <a class="navlink w-nav-link w-hidden-main" href="/search" style="max-width: 940px;">
                Search
            </a>
        </nav>

        <div class="w-nav-button">
            <div class="menuicon w-icon-nav-menu"></div>
        </div>
    </div>
</div>
