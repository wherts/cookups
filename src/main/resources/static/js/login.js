var emailExists = false;
function validateSignup() {
	var email = document.forms["signup-form"]["email"].value;
	var name = document.forms["signup-form"]["name"].value;
	var password = document.forms["signup-form"]["password"].value;
	
	var emailArray = email.split("@");
	$("#signup-form input[name=name]").removeAttr("style");
	$("#signup-form input[name=password]").removeAttr("style");
	$("#signup-form input[name=email]").removeAttr("style");
	if (name == "" || password == "" || email == "" ) {
		if (name == "") {
			$("#signup-form input[name=name]").attr("style", "border: 3px solid rgba(255, 184, 0, 0.77)");
		} 
		if (password == "") {
			$("#signup-form input[name=password]").attr("style", "border: 3px solid rgba(255, 184, 0, 0.77)");
		} 
		if (email == "") {
			$("#signup-form input[name=email]").attr("style", "border: 3px solid rgba(255, 184, 0, 0.77)");
		} 
		return false;
	} else if (emailArray.length < 2) {
		alert("Not a valid email address.");
		return false;
	}
	else if (emailArray[1] != "brown.edu") {
		alert("You must use a Brown University email address.");
		return false;
	} else if(password.length < 7) {
		alert("Your password must be at least 7 characters long");
		return false;
	} else if (emailExists) {
		alert("That email already exists");
		return false;
	}
	console.log(emailArray[0]);
	// setCookie(emailArray[0]);
}


// function setCookie(id) {
//     document.cookie = "id=" + id;
// }


var valid = false;
function validateLogin() {
	var email = document.forms["login-form"]["id"].value;
	var password = document.forms["login-form"]["password"].value;
	$("#login-form input[name=password]").removeAttr("style");
	$("#login-form input[name=id]").removeAttr("style");
	if (password == "" || email == "" ) {
		if (password == "") {
			$("#login-form input[name=password]").attr("style", "border: 3px solid rgba(255, 184, 0, 0.77)");
		}
		if (email == "") {
			$("#login-form input[name=id]").attr("style", "border: 3px solid rgba(255, 184, 0, 0.77)");
		} 
		return false;
	}
	if(!valid) {
		alert("That username and password is not valid");
	}
	return valid;

}



function checkLogin() {
	var email = document.forms["login-form"]["id"].value;
	var password = document.forms["login-form"]["password"].value;
	if (email == "" || password == "") return;
	var params = {
		uid:email,
		password:password 
	};
	valid = true;
	$.post("/authValidator", params, function(response) {
		var responseObject = JSON.parse(response);
		valid = responseObject.valid;
	});

}

function checkEmailExists() {
	var email = document.forms["signup-form"]["email"].value;
	if (email == "") return;
	var params = {
		email:email,
	};
	$.post("/emailValidator", params, function(response) {
		var responseObject = JSON.parse(response);
		emailExists = responseObject.valid;
		console.log(emailExists);
	});
}


$(document).ready(function() {
	$("#login-form").change(function() {
	checkLogin();
	});

	$("#signup-form input[name=email]").change(function() {
	checkEmailExists();
	})
});