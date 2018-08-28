<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<g:resource dir='js' file='webflow.js' />"></script>
<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
<script type="text/javascript">

   $(document).ready(function() {

	 $('.modal-link').click(function() {
       $('.modal-background').fadeIn();
     });
     
     $('.search-close').click(function() {
       $('.modal-background').fadeOut();
     });

     $('.help-modal-link').click(function() {
       $('.help-modal-background').fadeIn();
     });
     
     $('.help-close').click(function() {
       $('.help-modal-background').fadeOut();
     });
     
   });

</script>