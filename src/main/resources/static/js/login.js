function validateSignup() {
	var email = document.forms["signup-form"]["email"].value;
	
	var domain = email.split("@")[1];
	if (domain !== "brown.edu") {
		console.log("bad email domain");
		return false;
	}
}