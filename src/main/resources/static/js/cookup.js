
$('input:radio[name="type"]').on('change', function(){
    $('#romantic-opts').toggle();
});

function requestMatches() {
	var params = {
		romantic: $('#cookup-form input:radio[name="type"]').val();
		gender: $('#cookup-form input:range').val();
		orientation: $('#cookup-form input:radio[name="orientation"]').val();
	}
	console.log(params);
	$.post("/cookup", params, function(responseJSON) {
		console.log("Cookup requested");
	});
}
