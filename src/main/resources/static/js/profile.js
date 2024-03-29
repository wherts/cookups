var numberIngredients = 0;
var fridge;
var pantry;
var ingredients;
var favCuisines;
var personIngredients;

$(document).ready(function () {
	createProfile();
	$("input:radio[name='type']").on('change', function(){
	    $('#romantic-opts').toggle();
	});
});

function createProfile() {
	var addr = "/profileData/" + getCurrentID();
	$.get(addr, function(JSONresponse) {
		var response = JSON.parse(JSONresponse);
		fridge = response.fridge;
		pantry = response.pantry;
		ingredients = fridge.concat(pantry);
		ingredients = ingredients.sort();
		favCuisines = response.favCuisines;
		personIngredients = response.personIngredients;
		updateFridgeAndPantryNoEdit();

		var cuisine_options = generateCuisines(favCuisines);
		var ingredient_options = "";
		for (var i = 1; i <= ingredients.length; i++) {
			if (ingredients[i-1] in personIngredients) {
				ingredient_options += "<option value='"+i+"' selected='selected'>"+ingredients[i-1]+"</option>";
			} else {
				ingredient_options += "<option value='"+i+"'>"+ingredients[i-1]+"</option>";
			}
		}
		$("#fav-cuisines").html(cuisine_options);
		$("#fav-cuisines").tokenize({
				newElements: false,
		});

		$("#curr-ingredients").html(ingredient_options);
		$("#curr-ingredients").tokenize({
				newElements: false,
		});
		
		var len = $("#ingredientInput .TokensContainer .Token").length;

		for (var i = 1; i <= len; i++) {
			var ing = $("#ingredientInput .TokensContainer .Token:nth-child("+i+") span");
			if ($.inArray(ing.html(), fridge) != -1) {
				$("#fridge").append("<p class='ingredientName' id='"+ing.html()+"'>"+ing.html()+"</p>");
			}
			else if ($.inArray(ing.html(), pantry) != -1) {
				$("#pantry").append("<p class='ingredientName' id='"+ing.html()+"'>"+ing.html()+"</p>");
			}
			var amount = personIngredients[ing.html()];
			ing.append(" <input type='number' class='counter' name='"+ ing.html() +"' min='0.1' value='" + amount +"' step='0.1'>oz");
		}
		$(".counter").change(function() {
			var value = $(this).val();
			var name = $(this).attr("name");
			personIngredients[name] = parseFloat(value);
		});
		foodler();
		$(".about .TokensContainer").bind("DOMSubtreeModified", function () {
			foodler();
		});
		$("#ingredientInput .TokensContainer").bind("DOMSubtreeModified", function() {
			
			if ($("#ingredientInput .Token").html() != null && numberIngredients != $("#ingredientInput .Token").length) {
				var length = $("#ingredientInput .Token").length;
				var token = $("#ingredientInput .Token:nth-child("+length+")");
				var span = $("#ingredientInput .Token:nth-child("+length+") span");
				var ingred = span.html();
				personIngredients[ingred] = 0;
				if ($("#ingredientInput .Token:nth-child("+length+") .counter").length == 0) {
					if ($.inArray(ingred, fridge) != -1) {
						$("#fridge").append("<p class='ingredientName' id='"+ingred+"'>"+ingred+"</p>");
					}
					else if ($.inArray(ingred, pantry) != -1) {
						$("#pantry").append("<p class='ingredientName' id='"+ingred+"'>"+ingred+"</p>");
					}
					token.append(" <input type='number' style='font-size:.75em;' name='"+ ingred +"' class='counter new-counter' min='0.1' value='0.1' step='0.1'><span style='font-size:.75em;''>oz</span>");
					$("#ingredientInput .TokensContainer").click();
				}
				numberIngredients = $("#ingredientInput .Token").length;
			}
			$(".new-counter").change(function() {
				var value = $(this).val();
				var name = $(this).attr("name");
				personIngredients[name] = parseFloat(value);
			});
			updateFridgeAndPantry();
		});
	});
	$("#kitchen-link").attr("href", getCookie("id").split("@")[0]);
	closePopup();
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}


function updateFridgeAndPantryNoEdit() {
	var newHtmlFridge = "";
	var newHtmlPantry = "";
	var ings = $("#ingredientInput span");
	for (var i = 1; i <= ings.length; i++) {
		var toPut = $("#ingredientInput span:nth-child("+i+")").html();
		console.log(toPut);
		var ingredient = $("#ingredientInput span:nth-child("+i+")").attr("value");
		console.log(ingredient);
		if ($.inArray(ingredient, fridge) != -1) {
			newHtmlFridge += "<p class='ingredientName' id='"+ingredient+"'>"+toPut+"</p>";
		}
		else if ($.inArray(ingredient, pantry) != -1) {
			newHtmlPantry += "<p class='ingredientName' id='"+ingredient+"'>"+toPut+"</p>";
		}
	}
	$("#fridge").html(newHtmlFridge);
	$("#pantry").html(newHtmlPantry);
}

function updateFridgeAndPantry() {
	var newHtmlFridge = "";
	var newHtmlPantry = "";
	var counters = $("#ingredientInput .Token .counter");
	personIngredients = {}; //new hashmap for new ingredients (fridge and pantry have been updated)
	for (var i = 0; i < counters.length; i++) {
		var counter = counters[i];
		var name = counter.name;
		personIngredients[name] = parseFloat(counter.value);
		if ($.inArray(name, fridge) != -1) {
			newHtmlFridge += "<p class='ingredientName' id='"+name+"'>"+name+"</p>";
		}
		else if ($.inArray(name, pantry) != -1) {
			newHtmlPantry += "<p class='ingredientName' id='"+name+"'>"+name+"</p>";
		}
	}
	$("#fridge").html(newHtmlFridge);
	$("#pantry").html(newHtmlPantry);
}


function getCurrentID() {
	var profilePath = window.location.pathname;
	var split = profilePath.split("/");
	return split[split.length - 1];
}


$("#updateButton").click(function() {
	//send personIngredients
	var favoriteSpans = $("#about .Token span");
	var favCuisines = "";
	for (var i=1; i <= favoriteSpans.length; i++) {
		favCuisines += $("#about .Token:nth-child(" + i+ ") span").html();
		favCuisines += ","; //for splitting on backend
	}
		var postParams = {
		personIngs : JSON.stringify(personIngredients),
		favorites : favCuisines,
		image : image
	};
	var image = uploadPhoto(postParams);
	//console.log("IMAAAAAGE: "+image)
	var cuisines;

	var addr = "/updateProfile/" + getCurrentID();

	$.post(addr, postParams);
	alert("Profile Updated! Thanks for submitting new information.");
});

function uploadPhoto(params) {
	var file = document.getElementById('uploadPicture').files[0];
	if (file) {
		var reader = new FileReader();
 	    reader.onload = function(e) {
            // browser completed reading file - display it
            $('#profPic').css({background: "url(" + e.target.result + ")"});
            console.log(e.target.result);
		    //return e.target.result;
    		// return link;
    		postImage(params, e.target.result);
    	};
    	reader.readAsDataURL(file);

    	// ALSO WORKING:
    	var textReader = new FileReader();
	    return textReader.readAsText(file);


	    // formData = new FormData();
	    // if(!!file.type.match(/image.*/)){
	    //   formData.append("image", file);
	    //   return formData;
	    // } else{
	    //   alert('Not a valid image!');
	    //   return false;
	    // }
	} else {
		return false;
	}

}

function postImage(postParams, image) {
	console.log("IMAGEEEE 2: " + image);
	var addr = "/updateProfile/" + getCurrentID();
	postParams.image = image;
	$.post(addr, postParams);
}


function foodler() {
	var cuisines = ["Breakfast",
		"Burgers",
		"Catering",
		"Chinese",
		"Deli",
		"Italian",
		"Japanese",
		"Mediterranean",
		"Pizza",
		"Seafood",
		"Sushi",
		"Thai"];
	var questions = ["Too lazy to cook today?", 
	"Not feeling the same old same old?", 
	"Bummed that Cookups only has 15 recipes?",
	"Hate cooking?",
	"Don't have a kitchen?",
	"Did you wake up with a case of the Mondays?",
	"No friends to cook with?"];
	var resolutions = ["Not to fear!", "Oh, please!", "Hey, take a chill pill.",
		"Boy do we have a solution for you.", "You know what that means!"];
	var l = $(".about span").length;
	for (var i=0; i<l; i++) {
		var name = $(".about span")[i].innerHTML;
		if ($.inArray(name, cuisines) != -1) {
			var phrase = "<br><div id='foodler'>" + questions[getRandomInt(0, questions.length)] + " " + resolutions[getRandomInt(0, resolutions.length)];
			phrase += " Click below to get your favorite cuisine delivered immediately:";
			phrase += "<a target='_blank' href='http://www.foodler.com/providence/" + name + "'><br><img src='../assets/foodler.png' id='foodler-img' width='300px'></a>";
			phrase += "</div>";
			$("#foodler-container").html(phrase);
		}
	}
}

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min)) + min;
}

function generateCuisines(favCuisines) {
	var toReturn = "";
	var cuisines = ["American",
	            "African",
	            "Argentinian",
	            "Bagels",
	            "BBQ",
	            "Belgian",
	            "Brazilian",
	            "Breakfast",
	            "Brunch",
	            "Bubble Tea",
	            "Burgers",
	            "Cajun and Creole",
	            "Californian Cuisine",
	            "Cambodian",
	            "Caribbean",
	            "Cheesesteaks",
	            "Chinese",
	            "Churrascaria",
	            "Costa Rican",
	            "Crepes",
	            "Cuban",
	            "Deli",
	            "Dessert",
	            "Dim Sum & Dumplings",
	            "Diner",
	            "English",
	            "Farm to Table",
	            "Filipino",
	            "French",
	            "Frozen Yogurt Dessert",
	            "German",
	            "Gluten-Free",
	            "Greek",
	            "Haitian",
	            "Halal",
	            "Hawaiian",
	            "Healthy",
	            "Hot Dogs",
	            "Indian",
	            "Indonesian",
	            "Irish",
	            "Italian",
	            "Jamaican",
	            "Japanese",
	            "Juices",
	            "Korean",
	            "Kosher",
	            "Late-Night",
	            "Latin-American",
	            "Lebanese",
	            "Lunch-Specials",
	            "Malaysian",
	            "Mediterranean",
	            "Mexican",
	            "Middle-Eastern",
	            "Moroccan",
	            "Noodle-Shops",
	            "Organic",
	            "Pakistani",
	            "Peruvian",
	            "Pizza",
	            "Polish",
	            "Portuguese",
	            "Russian",
	            "Salads",
	            "Sandwiches-Wraps",
	            "Scandinavian",
	            "Seafood",
	            "Smoothies-Shakes",
	            "Soup",
	            "Southern and Soul",
	            "Spanish",
	            "Sri-Lankan",
	            "Steakhouse",
	            "Sushi",
	            "Sweets and Candy",
	            "Taiwanese",
	            "Thai",
	            "Turkish",
	            "Vegan",
	            "Vegetarian",
	            "Venezuelan",
	            "Vietnamese",
	            "Wings"];
	for (var i=1; i<=cuisines.length; i++) {
		if ($.inArray(cuisines[i-1], favCuisines) !== -1) {
			toReturn += "<option value='"+i+"' selected='selected'>"+cuisines[i-1]+"</option>";
		} else {
			toReturn += "<option value='"+i+"'>"+cuisines[i-1]+"</option>";
		}
	}
	return toReturn;
}

function showPopup() {
	$("#dateSignup").show();
	$("#dateSignup").lightbox_me({
		centered: true
	});
	//pull values and cacheSuitor using cookuphandler
}

function updateKitchen() {
	console.log("j");
	//send personIngredients
	var favoriteSpans = $("#about .Token span");
	var favCuisines = "";
	for (var i=1; i <= favoriteSpans.length; i++) {
		favCuisines += $("#about .Token:nth-child(" + i+ ") span").html();
		favCuisines += ","; //for splitting on backend
	}
		var postParams = {
		personIngs : JSON.stringify(personIngredients),
		favorites : favCuisines,
		image : image
	};
	var image = uploadPhoto(postParams);
	//console.log("IMAAAAAGE: "+image)
	var cuisines;
	console.log(postParams);
	var addr = "/updateProfile/" + getCurrentID();

	$.post(addr, postParams);
	alert("Profile Updated! Thanks for submitting new information.");
}

function closePopup() {
	$("#dateSignup").trigger('close');
	$("#dateSignup").hide();
}

function turnOnOrientation(type) {
	switch (type) {
		case "bi":
			$("#bi").prop('checked', true);
			break;
		case "gay":
			$("#gay").prop('checked', true);
			break;
		case "straight":
			$("#straight").prop('checked', true);
			break;
	}
}

function submitCookup() {
	var orientation = $("#dateSignup input:radio[name='orientation']:checked").val();
	if (orientation == null) {
		orientation = "queer";
	}
	var params = {
		romantic: $("#dateSignup input:radio[name='type']:checked").val() == "romantic",
		gender: $("#dateSignup input[name='gender']").val(),
		orientation: orientation
	}
	$.post("/cookup", params);
	closePopup();
}