/*price range*/

 $('#sl2').slider();

	var RGBChange = function() {
	  $('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
	};	
		
/*scroll to top*/

$(document).ready(function(){
	$(function () {
		$.scrollUp({
	        scrollName: 'scrollUp', // Element ID
	        scrollDistance: 300, // Distance from top/bottom before showing element (px)
	        scrollFrom: 'top', // 'top' or 'bottom'
	        scrollSpeed: 300, // Speed back to top (ms)
	        easingType: 'linear', // Scroll to top easing (see http://easings.net/)
	        animation: 'fade', // Fade, slide, none
	        animationSpeed: 200, // Animation in speed (ms)
	        scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
					//scrollTarget: false, // Set a custom target element for scrolling to the top
	        scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
	        scrollTitle: false, // Set a custom <a> title if required.
	        scrollImg: false, // Set true to use image
	        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
	        zIndex: 2147483647 // Z-Index for the overlay
		});
	});
});


//$(function(){
//	$('#nav-cart').hover(function(){
//		$('section').toggleClass('fade-down-opacity');
//		$('.header-main .nav-search-box input').toggleClass('fade-down-opacity');
//		$('.header-main .logo img').toggleClass('fade-down-opacity-x2');
//		$('.header-main .nav-link-default img').toggleClass('fade-down-opacity-x2');
//		$('.header-main .nav-search-box i').toggleClass('fade-down-opacity-x2');
//		$('.header-main').toggleClass('fade-down-bg');
//		//$('#nav-cart').toggleClass('fade-none');
//	})
//});

/* Product detail page: Thumbnail images swap */
$(function(){
	$('#product-image-thumbs img').click(function() { 
         var src = $(this).attr('src');
         $('#product-image-full').attr('src', src);
         $('img.current').removeClass('current');
         $(this).addClass('current');
     });
    
});


/* Accordion: Add plus/minus icons */
$(document).ready(function(){    
//    $('#accordProfile').on('shown', function () {
//       $(".icon-chevron-down").removeClass("icon-chevron-down").addClass("icon-chevron-up");
//    });
//    $('#accordProfile').on('hidden', function () {
//       $(".icon-chevron-up").removeClass("icon-chevron-up").addClass("icon-chevron-down");
//    });
	$('.collapse').on('shown.bs.collapse', function(){
		$(this).parent().find(".glyphicon-plus").removeClass("glyphicon-plus").addClass("glyphicon-minus");
		}).on('hidden.bs.collapse', function(){
		$(this).parent().find(".glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
		});
});