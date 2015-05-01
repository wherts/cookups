
$('input:radio[name="type"]').on('change', function(){
    $('#romantic-opts').toggle();
});


function submitCookup() {
	console.log("HERE");
	var orientation = $("#cookup-form input:radio[name='orientation']:checked").val();
	if (orientation == null) {
		orientation = "queer";
	}
	var params = {
		romantic: $("#cookup-form input:radio[name='type']:checked").val() == "romantic",
		gender: $("#cookup-form input[name='gender']").val(),
		orientation: orientation
	}
	console.log(params);
	$.post("/cookup", params, function(responseJSON) {
		var matches = JSON.parse(responseJSON);
		console.log("Cookup requested");
		people = "<p>Select a person to <em>cookup</em> with:</p>";
		for (var i = 0; i < matches.length; i++) {
			people += "<p>" + matches[i][1] + "</p>";
		}
		$("#matches").html(people);
	});
}
