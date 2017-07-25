/** jquery functions for scrollspy */
$(document).ready(function() {
// Highlight the top nav as scrolling occurs
    $('body').scrollspy({
        target: '.navbar-fixed-top',
        offset: 51
    });

    // Closes the Responsive Menu on Menu Item Click
    $('.navbar-collapse ul li a').click(
        function(){
            $('.navbar-toggle:visible').click();
        }
    );
});