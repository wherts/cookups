//this is where the user types
$("#box").show(0);
//this is where suggestions appear
$("#suggestionBox").hide(0);


$("#suggestionBox").bind('keyup', function(kPress) {
	switch (kPress.keyCode) {
		case 32:
			$(".suggestion").fadeOut(0);
			$("#list").empty();
			break;
		case 8:
			$("suggestion").fadeOut(0);
			$("#list").empty();
		default:
			var str = $(this).val();
			if (str.charCodeAt(str.length - 1) != 32) {
				getSuggestions(str);
			}
			break;
	}
});

$("#list").on('click', 'li', function() {
	var text = $(this).text();
	$("#box").val(text + ' ');
	$("#suggestionBox").fadeOut(0);
	$("#list").empty();
});

function getSuggestions(str) {
	if (str.length > 0) {
	var postParameters = {
			"input": str
	};
	$.post("/suggestions", postParameters, function(responseJSON){
		responseObject = JSON.parse(responseJSON);
		var sugg = responseObject.ranked;
		if (sugg.length > 0) {
			$("#suggestionBox").fadeIn(0);
			for (var i = 0; i < sugg.length; i++) {
				var s = $('<li class="result">' + sugg[i] + '</li>');
				$("#list").append('<li class="result">' + sugg[i] + '</li>');
			}
		} else {
			$("#suggestionBox").hide(0);
		}
		
	});
	$("#list").empty();
	}
}

