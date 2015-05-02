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
	document.getElementById('datePicker').value = new Date().toDateInputValue();
	document.getElementById('startTimePicker').value = new Date().toTimeInputValue();
	document.getElementById('endTimePicker').value = new Date().toEndTimeInputValue();
});


$.get("/allUsers", function(JSONresponse) {
	var response = JSON.parse(JSONresponse);
	var names = response.names;
	var ids = response.ids;
	console.log(response);
	var options = "";
	
	for (var i=1; i<=names.length; i++) {
		options += "<option value='"+i+"'>"+names[i-1]+" ("+ids[i-1]+")</span></option>";
	}
	$("#add-chefs").html(options);
	$("#add-chefs").tokenize({
			newElements: false,
	});
});


function submitCookFriends() {
	var chefs="";
	var delim=",";
    $( "select option:selected" ).each(function() {
    	var regex = /\(([^)]+)\)/;
    	var text = regex.exec($(this).text())[1];
    	console.log(text);
        chefs += text+delim;
      });
	var params = {
		name: $('#friends-form input[name=name]').val(),
		date: $('#friends-form input[name=date]').val(),
		timeStart:  $('#friends-form input[name=time_start]').val(),
		timeEnd:  $('#friends-form input[name=time_end]').val(),
		chefs: chefs
	}
	
	console.log(params);
	
	$.post("/makemeal", params, function(responseJSON) {
		var mealLink = JSON.parse(responseJSON);
		$('#mealLink').html("<a href='"+mealLink+"'>Meal </a>");
		
		console.log(mealLink);
	});
}
