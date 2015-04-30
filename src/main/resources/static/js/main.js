$('#search-icon').on('click', function(e) {
	var term = $('#search-input').val();
	$.post("/search", {term: term}, function(responseJSON) {
		console.log("searched");
	})
})

$(document).ready(function () {
  $(".hoverli").hover(
	  function () {
	     $('ul.dropdown-menu').stop().slideDown('medium');
	  }, 
	  function () {
	     $('ul.dropdown-menu').stop().slideUp('medium');
	  }
	);
});
