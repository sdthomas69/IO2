<shiro:isLoggedIn>
	<g:set 
		var="link" 
		value="${createLink(uri: '/')}" 
	/>
</shiro:isLoggedIn>
<shiro:isNotLoggedIn>
	<g:set var="link" value="${createLink(uri: '/index.html')}" />
</shiro:isNotLoggedIn>

<div class="footersection">
	<div class="footernavbar w-nav" data-animation="default" data-collapse="none" data-contain="1" data-duration="400">
		<div class="footer-nav-container w-container">
			<a class="sea-state-link-box w-inline-block" href="${link}">
				<img class="footer-logo-image" src="<g:resource dir='images' file='EEW-LOGO-small.png' />" width="65">
			</a>
			<%--<div class="footericons">
				<a class="socialicons w-inline-block" href="https://www.facebook.com/groups/2201440365/members/" target="_blank" class="socialicons w-inline-block"">
					<img width="96" src="<g:resource dir='images' file='icon-fb.png' />" class="socialicons">
				</a>
				<a href="https://www.youtube.com/user/uwhuskies" target="_blank" class="socialicons w-inline-block">
					<img width="96" src="<g:resource dir='images' file='icon-youtube.png' />" class="socialicons">
				</a>
				<a href="https://www.linkedin.com/groups/6793605" target="_blank" class="socialicons w-inline-block">
					<img width="96" src="<g:resource dir='images' file='icon-lin.png' />" class="socialicons">
				</a>
			</div>--%>
			<div class="w-nav-button">
				<div class="w-icon-nav-menu"></div>
			</div>
			<a href="http://www.washington.edu" class="w-inline-block">   
				<img class="uwlogo" src="<g:resource dir='images' file='Wordmark_center_Purple_Hex-300x20 copy.png' />" >
			</a>
			<div class="footer-copyright">© <g:formatDate format="yyyy" date="${new Date()}"/> Interactive Oceans</div>
		</div>
	</div>
</div>

<div class="w-section modal-background">
	<div class="w-clearfix modal-window">
		<a class="search-close" href="#">Close</a>
		<div class="w-form w-clearfix form-wrapper">
			%{--<g:remoteField
				class="w-clearfix searchform"
				controller="search"
				action="dropDownSearch" 
				update="[success:'list1', failure:'error1']" 
				name="q" 
				paramName="q" 
			/>--}%
			<div id="list1"></div>
			<div id="error1"></div>
		</div>
	</div>
</div>

<g:render template="/layouts/jscript" />
<%-- End:Footer--%>