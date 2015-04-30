$(document).ready(function () {
	$.get("/profileData", function(JSONresponse) {
		console.log(JSONresponse);
		var response = JSON.parse(JSONresponse);
		var recipes = response.recipes;
		var ingredients = response.ingredients;
		var favRecipes = response.favRecipes;
		var personIngredients = response.personIngredients;
		console.log(recipes);
		console.log(ingredients);
		console.log(favRecipes);
		console.log(personIngredients);
		
		var recipe_options = "";
		var ingredient_options = "";
		
		for (var i=1; i<=recipes.length; i++) {
			if ($.inArray(recipes[i-1], favRecipes) !== -1) {
				recipe_options += "<option value='"+i+"' selected='selected'>"+recipes[i-1]+"</option>";
			} else {
				recipe_options += "<option value='"+i+"'>"+recipes[i-1]+"</option>";
			}
		}
		
		for (var i=1; i<=recipes.length; i++) {
			if ($.inArray(ingredients[i-1], personIngredients) !== -1) {
				ingredient_options += "<option value='"+i+"' selected='selected'>"+ingredients[i-1]+"</option>";
			} else {
				ingredient_options += "<option value='"+i+"'>"+recipes[i-1]+"</option>";
			}
		}
		
		$("#fav-recipes").html(recipe_options);
		$("#fav-recipes").tokenize({
				newElements: false,
		});
		
		$("#curr-ingredients").html(ingredient_options);
		$("#curr-ingredients").tokenize({
				newElements: false,
		});
	});
});