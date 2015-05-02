function validateSignup() {
	var email = document.forms["signup-form"]["email"].value;
	var name = document.forms["signup-form"]["name"].value;
	var password = document.forms["signup-form"]["password"].value;
	
	var emailArray = email.split("@");
	
	if (name === "" || password === "" || email === "" ) {
		if (name === "") {
			$("#signup-form input[name=name]").attr("style", "border:5px solid red");
		}
		if (password === "") {
			$("#signup-form input[name=email]").attr("style", "border:5px solid red");
		}
		if (email === "") {
			$("#signup-form input[name=password]").attr("style", "border:5px solid red");
		}
		return false;
	} else if (emailArray.length < 2) {
		alert("Not a valid email address.");
		return false;
	}
	else if (emailArray[1] !== "brown.edu") {
		alert("You must use a Brown University email address.");
		return false;
	}
}

function validateLogin() {
	var email = document.forms["login-form"]["id"].value;
	var password = document.forms["login-form"]["password"].value;
	if (password === "" || email === "" ) {
		if (password === "") {
			$("#login-form input[name=id]").attr("style", "border:5px solid red");
		}
		if (email === "") {
			$("#login-form input[name=password]").attr("style", "border:5px solid red");
		}
		return false;
	}
}