var numberIngredients = 0;
var fridge;
var pantry;
var ingredients;
var favCuisines;
var personIngredients;
var updatedIngredients = {};

$(document).ready(function () {
	$.get("/profileData", function(JSONresponse) {
		console.log(JSONresponse);
		var response = JSON.parse(JSONresponse);
		fridge = response.fridge;
		pantry = response.pantry;
		ingredients = fridge.concat(pantry);
		ingredients = ingredients.sort();
		favCuisines = response.favCuisines;
		personIngredients = response.personIngredients;
		console.log(ingredients);
		console.log(favCuisines);
		console.log(personIngredients);
		
		var cuisine_options = generateCuisines(favCuisines);
		var ingredient_options = "";
		
		for (var i=1; i<=ingredients.length; i++) {
			if ($.inArray(ingredients[i-1], personIngredients) !== -1) {
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
		$("#ingredientInput .TokensContainer").bind("DOMSubtreeModified", function() {
			
			if ($("#ingredientInput .Token").html() != null && numberIngredients != $("#ingredientInput .Token").length) {
				$("#ingredientInput .Class").bind("click", function () {
					console.log("HI");
					var dataVal = this.parent().attr("data-value");
					console.log(dataVal);
					
					
				});
				var length = $("#ingredientInput .Token").length;
				var token = $("#ingredientInput .Token:nth-child("+length+")");
				var span = $("#ingredientInput .Token:nth-child("+length+") span");
				var ingred = span.html();
				if ($("#ingredientInput .Token:nth-child("+length+") .counter").length == 0) {
					if ($.inArray(ingred, fridge) != -1) {
						$("#fridge").append("<p class='ingredientName' id='"+ingred+"'>"+ingred+"</p>");
					}
					else if ($.inArray(ingred, pantry) != -1) {
						$("#pantry").append("<p class='ingredientName' id='"+ingred+"'>"+ingred+"</p>");
					}
					token.append(" <input type='number' class='counter' min='0.1' value='0.1' step='0.1'>oz");
					$("#ingredientInput .TokensContainer").click();
				}
				numberIngredients = $("#ingredientInput .Token").length;
			}
		});
	});
});

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