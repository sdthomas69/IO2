<g:if test="${objects}">
	<%@ page import="org.springframework.util.ClassUtils" %>
	<g:set var="defaultThumbnail" value="/images/no-primary-image.png" />
	<div class="w-row">
		<g:each in="${objects}" var="object" status="i" >
			<blackFish:tagWrapper 
				tag="div" 
				className="w-row file-list-row"
				iterations="4"
				counter="${i}"
				total="${objects.getTotalCount()}">
				<g:set var="className" value="${ClassUtils.getShortName(object.getClass()).replaceAll('_', '')}" />
				<g:set var="linkName" value="${className[0].toLowerCase() + className[1..-1]}" />
				<g:set var="thumbnail" value="${defaultThumbnail}" />
		        <g:if test="${className == 'File'}">
		            <g:set var="thumbnail" value="${object?.smallImageSquared ?: defaultThumbnail}" />
		        </g:if>
		        <g:else>
		            <g:set var="thumbnail" value="${object?.primaryImage?.smallImageSquared ?: defaultThumbnail}" />
		        </g:else>
		        <div class="w-col w-col-3 spacing">
					<a href="<blackFish:setLink object="${object}" name="${linkName}" />">
				    	<img src="<g:resource file='${thumbnail}' />" alt="${object.title}" />
					</a>
					<div class="file-list-text">
						<a href="<blackFish:setLink object="${object}" name="${linkName}" />">
							${object.title}
						</a>
					</div>
				</div>
			</blackFish:tagWrapper>
		</g:each>
		<div class="paginateButtons">                           
		    <g:paginate action="index" total="${objects.getTotalCount()}" params="${params}" />
		</div>
	</div>
</g:if>