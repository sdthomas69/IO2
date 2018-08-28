<g:set var="list" value="${['Acting Associate Professor', 
		'Acting Assistant Professor', 
		'Acting Instructor', 
		'Acting Professor', 
		'Adjunct Assistant Professor', 
		'Adjunct Associate Professor',
		'Adjunct Professor',
		'Affiliate Assistant Professor',
		'Affiliate Associate Professor',
		'Affiliate Professor',
		'Assistant Professor',
		'Associate Professor',
		'Associate Professor Emeritus',
		'Graduate Student',
		'Lecturer',
		'Lecturer Emeritus',
		'Postdoctoral Scholar',
		'Principal Lecturer',
		'Principal Lecturer Emeritus',
		'Professor',
		'Professor Emeritus',
		'Research Associate Professor Emeritus',
		'Research Professor Emeritus',
		'Senior Lecturer',
		'Senior Lecturer Emeritus',
		'Undergraduate Student']}" />

<g:select 
	class="w-select"
	name="position" 
	value="${userInstance.position}" 
	from="${list}" 
	noSelection="['':'None']" 
/>