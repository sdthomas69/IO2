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