var servings = parseFloat(1);

$(document).ready(function () {
	servings = window.location.href.split("%26")[1];
	$("#servings").val(servings);
	$("#servings").change(function () {
		updateNumbers();
	});
	updateNumbers();
});

function changeMeasurements(type) {
	$(".ingredient-text").hide();
	$("."+type).show();
}

function updateNumbers() {
	servings = parseFloat($("#servings").val());
	var nums = $(".ing-amount");
	for (var i=0; i<nums.length; i++) {
		num = $(".ing-amount")[i];
		unit = parseFloat(num.parentNode.getAttribute("amount"));
		console.log(unit);
		num.innerHTML = round(parseFloat(servings) * parseFloat(unit), 3);
	}
}

function round(num, places) {
    var multiplier = Math.pow(10, places);
    return Math.round(num * multiplier) / multiplier;
}