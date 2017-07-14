/*price range*/

 $('#sl2').slider();

	var RGBChange = function() {
	  $('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
	};	
		
/* Mobile nav: open either menu or search, not both */
$(function(){
	$('.btn-navbar-search').click(function(){
		if ($('.navbar-main').hasClass('in')) {
			$('.navbar-main').removeClass('in').addClass('collapse');
		} 
	});
	$('.btn-navbar-main').click(function(){
		//console.log('btn clicked');
		if ($('.navbar-search').hasClass('in')) {
			$('.navbar-search').removeClass('in').addClass('collapse');
		} 
	});
});

/* Product detail page: Thumbnail images swap */
$(function(){
	$('#product-image-thumbs img').click(function() { 
         var src = $(this).attr('src');
         $('#product-image-full').attr('src', src);
         $('img.current').removeClass('current');
         $(this).addClass('current');
     });
    
});

/* Stop auto-scroll on carousel */
$('.carousel.no-autoscroll').carousel({
    interval: false
}) 

/* Show/hide shipping address is same as billing address fields */
$(function(){
	$('#ship_address_fields').css('display','none');
	$('#ship_address_same').click(function(){
		if ($(this).is(':checked')) {
			$('#ship_address_fields').css('display','none');
		} else {
			$('#ship_address_fields').css('display','block');
		};
	});
});


/* Accordion: Add plus/minus icons */
$(document).ready(function(){    
	$('.plus-minus').click(function(){
		if ($(this).find('.fa').hasClass('fa-plus')){
			$(this).find('.fa').removeClass("fa-plus").addClass("fa-minus");
		} else {
			$(this).find('.fa').removeClass("fa-minus").addClass("fa-plus");
		};
	});
});


/* Initialise validation on forms */
$(document).ready(function(){    
	$('form').validate({
		rules: {
			bill_country: "required",
			bill_first_name: "required",
			bill_second_name: "required",
			bill_address_1: "required",
			bill_city: "required",
			bill_zipcode: "required",
			bill_email: {
				required: true,
				email: true
			},
			bill_phone: {
				required: "#ship_address_same:unchecked"
			},
			ship_country: {
				required: "#ship_address_same:unchecked"
			},
			ship_first_name: {
				required: "#ship_address_same:unchecked"
			},
			ship_second_name: {
				required: "#ship_address_same:unchecked"
			},
			ship_address_1: {
				required: "#ship_address_same:unchecked"
			},
			ship_city: {
				required: "#ship_address_same:unchecked"
			},
			ship_zipcode: {
				required: "#ship_address_same:unchecked"
			},
			ship_email: {
				required: "#ship_address_same:unchecked",
				email: true
			},
			ship_phone: {
				required: "#ship_address_same:unchecked"
			}
		},
		messages: {
			bill_email: "Please enter a valid email address",
			ship_email: "Please enter a valid email address"
		}
	});
});

/* switch between grid and list on product listing */
$('.list-table').on('click',function(e) {
    if ($(this).hasClass('btn-grid')) {
        $('.btn-grid').removeClass('toggle-off').addClass('toggle-on');
        $('.btn-list').removeClass('toggle-on').addClass('toggle-off');
    	$('.product-list-items .list-table-item').removeClass('list').addClass('grid');
        $('.product-list-items .list-table-item').removeClass('col-sm-12').addClass('col-sm-4');
    }
    else if($(this).hasClass('btn-list')) {
    	$('.btn-list').removeClass('toggle-off').addClass('toggle-on');
        $('.btn-grid').removeClass('toggle-on').addClass('toggle-off');
    	$('.product-list-items .list-table-item').removeClass('grid').addClass('list');
        $('.product-list-items .list-table-item').removeClass('col-sm-4').addClass('col-sm-12');
    }
});

/* More link on product listing filters, activate when more than 5 options */
$(document).ready(function(){    
	$('.more-items').filter(function() {
		   return ($(this).children('li').length <= 5);
		}).addClass('less-than-5');
	$('.less-than-5').parent('.panel-body').find('.more-link').hide();
	$('.panel-body').find('.more-items li:gt(4)').hide();
	$('.panel-body').find('.less-link').hide();
	$('.more-link').on('click', function(event) {
	  $(event.target).parent('.panel-body').find('.more-items li:gt(4)').show();
	  $(this).hide();
	  $(event.target).parent('.panel-body').find('.less-link').show();
	});
	$('.less-link').on('click', function(event) {
	  $(event.target).parent('.panel-body').find('.more-items li:gt(4)').hide();
	  $(this).hide();
	  $(event.target).parent('.panel-body').find('.more-link').show();
	});
});
