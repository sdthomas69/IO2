<a class="w-nav-link sidebar-nav-link" href="<blackFish:setLink object='${thisMenu}' name='story' />">
	${thisMenu.title}
</a>
<g:if test="${thisMenu.children}">
    <div class="w-dropdown w-clearfix sidebar-nav-dropdown" data-delay="0">
		<div class="w-dropdown-toggle sidebar-nav-link">
			<div>Dropdown</div>
			<div class="w-icon-dropdown-toggle"></div>
		</div>
		<nav class="w-dropdown-list w-clearfix sidebar-dd-nav-link-list">
			<g:render template="/story/menu" collection="${thisMenu.children?.sort()}" var="thisMenu" />
		</nav>
	</div>
</g:if>
