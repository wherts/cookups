$('#cookup-form').submit(function(e) {
	console.log("cookup form submitted");

	$.post("/cookup", params, function(responseJSON) {

	})
});

$('#friends-form').submit(function(e) {
	console.log("cook with friends form submitted");
	
	var params = {
		name: $('#friends-form input[name=name]').val(),
		date: $('#friends-form input[name=date]').val(),
		timeStart:  $('#friends-form input[name=time_start]').val(),
		timeEnd:  $('#friends-form input[name=time_end]').val(),
		timeStart:  $('#friends-form input[name=time_start]').val(),
		chefs:  $('#friends-form input[name=chefs]').val(),
	}
	$.post("/cookwfriends", params, function(responseJSON) {

	})
	
});


$("#add-chefs").bind('keyup', function(event) {
	var words = $(this).val();
	var postParameters = {input: words};
	$.post("/autoPeople", postParameters, function(responseJSON){
		
		
	})
	$("#add-chefs").tokenize();
}