$(document).ready(function () {
	$('#search-icon').on('click', function(e) {
		var term = $('#search-input').val();
		$.post("/search", {term: term}, function(responseJSON) {
			console.log("searched");
		});
	});
	
	$(".hoverli").hover(
	  function () {
	     $('ul.dropdown-menu').stop().slideDown('medium');
	  }, 
	  function () {
	     $('ul.dropdown-menu').stop().slideUp('medium');
	  }
	);

	function docFill(){
		if($(document).height() <= $(window).height()){
			$(".last-div").css("height",$(window).height()-$("body").height()+$(".last-div").height());
		}else{
		    $(".last-div").css("height","auto");
		};
	}
	
	$(document).ready(docFill);
	$(window).resize(docFill);
});