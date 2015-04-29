$("#Street1").bind('keyup', function(event){
	var element = document.getElementById("suggestionsS1");
	var words = $(this).val();

	if (words.length>0) {
		var postParameters = {input: words};
		
		$.post("/autocomplete", postParameters, function(responseJSON){
	
			responseObject = JSON.parse(responseJSON);
			
			var str = "";
			for (var i=0; i<responseObject.suggestions.length; i++) {
				str += "<p class=sugS1>"+responseObject.suggestions[i]+"</p>";
			}

			element.innerHTML = str;
			$("#suggestionsS1").show(0);
		})
	} else {
		$("#suggestionsS1").hide(100);
	}

});


$("#Cross1").bind('keyup', function(event){
	var element = document.getElementById("suggestionsC1");
	var words = $(this).val();

	if (words.length>0) {
		var postParameters = {input: words};
		
		$.post("/autocomplete", postParameters, function(responseJSON){
	
			responseObject = JSON.parse(responseJSON);
			
			var str = "";
			for (var i=0; i<responseObject.suggestions.length; i++) {
				str += "<p class=sugC1>"+responseObject.suggestions[i]+"</p>";
			}

			element.innerHTML = str;
			$("#suggestionsC1").show(0);
		})
	} else {
		$("#suggestionsC1").hide(100);
	}
});

$("#Street2").bind('keyup', function(event){
	var element = document.getElementById("suggestionsS2");
	var words = $(this).val();

	if (words.length>0) {
		var postParameters = {input: words};
		
		$.post("/autocomplete", postParameters, function(responseJSON){
	
			responseObject = JSON.parse(responseJSON);
			
			var str = "";
			for (var i=0; i<responseObject.suggestions.length; i++) {
				str += "<p class=sugS2>"+responseObject.suggestions[i]+"</p>";
			}

			element.innerHTML = str;
			$("#suggestionsS2").show(0);
		})
	} else {
		$("#suggestionsS2").hide(100);
	}

});


$("#Cross2").bind('keyup', function(event){
	var element = document.getElementById("suggestionsC2");
	var words = $(this).val();

	if (words.length>0) {
		var postParameters = {input: words};
		
		$.post("/autocomplete", postParameters, function(responseJSON){
	
			responseObject = JSON.parse(responseJSON);
			
			var str = "";
			for (var i=0; i<responseObject.suggestions.length; i++) {
				str += "<p class=sugC2>"+responseObject.suggestions[i]+"</p>";
			}

			element.innerHTML = str;
			$("#suggestionsC2").show(0);
		})
	} else {
		$("#suggestionsC2").hide(100);
	}
});

$(document).on('mouseenter', '.sugS1', function(event) {
	$(this).addClass("hovered");
});

$(document).on('mouseleave', '.sugS1', function(event) {
	$(this).removeClass("hovered");
});

$(document).on('mouseenter', '.sugC1', function(event) {
	$(this).addClass("hovered");
});

$(document).on('mouseleave', '.sugC1', function(event) {
	$(this).removeClass("hovered");
});

$(document).on('mouseenter', '.sugS2', function(event) {
	$(this).addClass("hovered");
});

$(document).on('mouseleave', '.sugS2', function(event) {
	$(this).removeClass("hovered");
});

$(document).on('mouseenter', '.sugC2', function(event) {
	$(this).addClass("hovered");
});

$(document).on('mouseleave', '.sugC2', function(event) {
	$(this).removeClass("hovered");
});

$(document).on('click', '.sugS1', function(event) {
	var text = $(this).text();
	$("#Street1").val(text);
	$("#suggestionsS1").hide(100);
});

$(document).on('click', '.sugC1', function(event) {
	var text = $(this).text();
	$("#Cross1").val(text);
	$("#suggestionsC1").hide(100);
});

$(document).on('click', '.sugS2', function(event) {
	var text = $(this).text();
	$("#Street2").val(text);
	$("#suggestionsS2").hide(100);
});

$(document).on('click', '.sugC2', function(event) {
	var text = $(this).text();
	$("#Cross2").val(text);
	$("#suggestionsC2").hide(100);
});

$("body").on('click', function(event) {
	$("#suggestionsS1").hide(100);
	$("#suggestionsC1").hide(100);
	$("#suggestionsS2").hide(100);
	$("#suggestionsC2").hide(100);
});