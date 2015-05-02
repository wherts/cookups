Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

Date.prototype.toTimeInputValue = (function() {
    var local = new Date(this);
    local.setHours(this.getHours() + 2);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(11,16);
});

Date.prototype.toEndTimeInputValue = (function() {
    var local = new Date(this);
    local.setHours(this.getHours() + 4);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(11,16);
});

$('input:radio[name="type"]').on('change', function(){
    $('#romantic-opts').toggle();
});


function submitCookup() {
	var orientation = $("#cookup-form input:radio[name='orientation']:checked").val();
	if (orientation == null) {
		orientation = "queer";
	}
	var params = {
		romantic: $("#cookup-form input:radio[name='type']:checked").val() == "romantic",
		gender: $("#cookup-form input[name='gender']").val(),
		orientation: orientation
	}
	$.post("/cookup", params, function(responseJSON) {
		var matches = JSON.parse(responseJSON);
		people = "<p>Select a person to <em>cookup</em> with:</p><table><tr>";
		var length = Math.min(matches.length, 5);
		for (var i = 0; i < length; i++) {
			var link = "../assets/people/" + matches[i][0].split("@")[0]+".jpg";
			people += "<td class='person-option'><div class='match' style='background-image:url("+link+")'></div><br>"+matches[i][1]+" ("+matches[i][0]+") "+"<td>";
		}
		
		people += "</tr></table><div id='cookup-meal-form'>"
			+ "<div class='form-entry'>Meal Name<br><input class='inset' type='text' name='name'></div>"
			+ "<div class='form-entry'>Date<br><input type='date' id='datePicker' name='date'></div>"
			+ "<div class='form-entry'>Time<br><input type='time' id='startTimePicker' name='time_start'>"
			+ "to <input type='time' id='endTimePicker' name='time_end'></div>" 
			+ "<div class='btn-container'><input type='button' class='btn' onClick=makeCookupMeal() value='Make Cookup!'></div>" 
			+ "</div>";
		$("#matches").html(people);
		document.getElementById('datePicker').value = new Date().toDateInputValue();
		document.getElementById('startTimePicker').value = new Date().toTimeInputValue();
		document.getElementById('endTimePicker').value = new Date().toEndTimeInputValue();
		setPersonListener();
	});
}

function makeCookupMeal() {
	var name = $('#cookup-meal-form input[name=name]').val();
	var date = $('#cookup-meal-form input[name=date]').val();
	var timeStart = $('#cookup-meal-form input[name=time_start]').val();
	var timeEnd = $('#cookup-meal-form input[name=time_end]').val();
	
	if (document.getElementsByClassName("border").length < 1) {
		alert("Please select a person to cookup with.");
	} else if (date === "") {
		alert("Please select a date for the meal.");
	} else if (timeStart === "") {
		alert("Please select a start time for the meal.");
	} else if (name === "") {
		alert("Please choose a name for the meal.");
	}
	else {
		var text = document.getElementsByClassName("border")[0].textContent;
    	var regex = /\(([^)]+)\)/;
    	var chef = regex.exec(text)[1];
    	console.log(chef);
		if (timeEnd === "") {timeEnd = null};
		var params = {
				name: name,
				date: date,
				timeStart: timeStart,
				timeEnd: timeEnd,
				chefs: chef
		}
		console.log(params);
		$.post("/makemeal", params, function(responseJSON) {
			var mealLink = JSON.parse(responseJSON);
			$('#mealLink').html("<a href='"+mealLink+"'>Meal </a>");
			
			console.log(mealLink);
		});	
	}
}

function setPersonListener() {
	$('.person-option').on('click', function(){
		removeBorders();
		$(this).addClass("border");
	});
}

function removeBorders() {
	$('.person-option').each(function(i) {
		$(this).removeClass("border");
	});
}
