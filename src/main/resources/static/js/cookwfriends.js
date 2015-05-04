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

$(document).ready(function () {
	$("#viewCookupBtn").hide(); //hide cookup button
	document.getElementById('datePicker').value = new Date().toDateInputValue();
	document.getElementById('startTimePicker').value = new Date().toTimeInputValue();
	document.getElementById('endTimePicker').value = new Date().toEndTimeInputValue();
});


$.get("/allUsers", function(JSONresponse) {
	var response = JSON.parse(JSONresponse);
	var names = response.names;
	var ids = response.ids;
	var options = "";
	
	for (var i=1; i<=names.length; i++) {
		if (ids[i-1] != $("#kitchen-link").attr("href").split("/")[2] + "@brown.edu") {
			options += "<option value='"+i+"'>"+names[i-1]+" ("+ids[i-1]+")</span></option>";
		}
	}
	$("#add-chefs").html(options);
	$("#add-chefs").tokenize({
			newElements: false,
	});

});

function validateForm(chefs) {
	var mealName = $("#friends-form input[name=name]").val();
	var date = $('#friends-form input[name=date]').val();
	var startTime = $('#friends-form input[name=time_start]').val();
	var endTime = $('#friends-form input[name=time_end]').val();
	
	$("#friends-form input[name=name]").removeAttr("style");
	$("#friends-form input[name=date]").removeAttr("style");
	$("#friends-form input[name=time_start]").removeAttr("style");
	$("#friends-form input[name=time_end]").removeAttr("style");
	$('.TokensContainer').removeAttr("style");

	if (mealName == "") {
		$("#friends-form input[name=name]").attr("style", "border:5px solid red");
		return false;
	}

	if (date == "") {
		$("#friends-form input[name=date]").attr("style", "border:5px solid red");
		return false;
	}

	if (startTime == "") {
		$('#friends-form input[name=time_start]').attr("style", "border:5px solid red");
		return false;
	}

	if (endTime == "") {
		$('#friends-form input[name=time_end]').attr("style", "border:5px solid red");
		return false;
	}
	if (chefs == "") {
		$('.TokensContainer').attr("style", "border:5px solid red");
		return false;
	}
	return true;
}

function submitCookFriends() {

	var chefs="";
	var delim=",";
    $( "select option:selected" ).each(function() {
    	var regex = /\(([^)]+)\)/;
    	var text = regex.exec($(this).text())[1];
    	console.log(text);
        chefs += text+delim;
      });
	if (!validateForm(chefs)) {
		return;
	}
    var date = $('#friends-form input[name=date]').val();
    var today = new Date().toDateInputValue();

    if (date < today) {
    	console.log('error');
    	$('#date-label').after("<p style='color:red; margin:0'>Please enter a date in the future.</p>");
    } else {
		var params = {
			name: $('#friends-form input[name=name]').val(),
			date: date,
			timeStart:  $('#friends-form input[name=time_start]').val(),
			timeEnd:  $('#friends-form input[name=time_end]').val(),
			chefs: chefs
		}
		
		console.log(params);
		
		$.post("/makemeal", params, function(responseJSON) {
			var url = window.location.href;
			var path = window.location.pathname;
			var prefix = url.replace(path, "");
			var mealLink = prefix + JSON.parse(responseJSON);
			window.location.replace(mealLink);
		});
	}
}
