var numberIngredients = 0;
var fridge;
var pantry;
var ingredients;
var favCuisines;
var personIngredients;

$(document).ready(function () {
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
				// personIngredients[name] = parseFloat(value);
				personIngredients[name] = parseFloat(value);
			});
			updateFridgeAndPantry();
		});
	});
});

function updateFridgeAndPantryNoEdit() {
	var newHtmlFridge = "";
	var newHtmlPantry = "";
	var ings = $("#ingredientInput span");
	console.log(ings);
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
	
	// for (var i = 0; i < cuisines.length; i++) {
	// 	console.log(cuisines[i]);
	// 	cuisines += cuisines[i];
	// 	cuisines += "$";
	// }
	var cuisines;
	var addr = "/updateProfile/" + getCurrentID();
	var postParams = {
		personIngs : JSON.stringify(personIngredients),
		 favorites : favCuisines
	};
	$.post(addr, postParams);
});