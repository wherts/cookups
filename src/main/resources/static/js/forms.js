
$.get("/allUsers", function(JSONresponse) {
	var users = JSON.parse(JSONresponse);
	var options = "";
	console.log(users);
	
	for (var i=1; i<=users.length; i++) {
		options += "<option value='"+i+"'>"+users[i-1]+"</option>";
	}
	$("#add-chefs").html(options);
	$("#add-chefs").tokenize({
			newElements: false,
	});
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

function validateSignup() {
	var email = document.forms["signup-form"]["email"].value;
	
	var domain = email.split("@")[1];
	if (domain !== "brown.edu") {
		console.log("bad email domain");
		return false;
	}
}

$("input[name='type']").on('change', function(){
	console.log("why");
    $('#romantic-opts').toggle();
});