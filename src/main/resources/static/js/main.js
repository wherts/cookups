$('#search-icon').on('click', function(e) {
	var term = $('#search-input').val();
	$.post("/search", {term: term}, function(responseJSON) {
		console.log("searched");
	})
})
