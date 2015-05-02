var priceLow;
var priceHigh;
var percentLow;
var percentHigh;
var mealPath;

$(document).ready(function() {
		mealPath = window.location.pathname;
		$( "#priceSlider" ).slider({
			range: true,
			min: 0,
			max: 500,
			values: [ 0, 500 ],
			slide: function( event, ui ) {
				$( "#priceAmount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
			}
		});
		priceLow = $( "#priceSlider" ).slider( "values", 0 );
		priceHigh = $( "#priceSlider" ).slider( "values", 1 );
		$( "#priceAmount" ).val( "$" + priceLow + " - $" + priceHigh );
		
		$( "#percentSlider" ).slider({
			range: true,
			min: 0,
			max: 100,
			values: [ 0, 100 ],
			slide: function( event, ui ) {
				$( "#percentAmount" ).val(ui.values[ 0 ] + "% - " + ui.values[ 1 ] + "%");
			}
		});
		percentLow = $( "#percentSlider" ).slider( "values", 0 );
		percentHigh = $( "#percentSlider" ).slider( "values", 1 );
		$( "#percentAmount" ).val(percentLow + "% - " + percentHigh + "%");
	}); //ends ready

function changeSort() {
	var sortType = $("#sortType").val();

	var params = {
		sort : sortType,
		priceLow : priceLow,
		priceHigh : priceHigh,
		percentLow : percentLow,
		percentHigh : percentHigh
	};
	var postAddr = mealPath + "/" + $.param(params);
	$.post(postAddr, params, function (responseObject) {
		var newHtml = "";
		var recipes = JSON.parse(responseObject);
		for (var i = 0; i < recipes.length; i++) {
			newHtml += "<#assign link = '../assets/recipes/'' + " + recipes[i].id.replace("/", "$") + "+'.jpg'><td>";
	      	newHtml += "<div class='recipe-pic-container'><img class='recipe-pic' src='${link}'></div>";
	      	newHtml += "<p class='recipe-title'>" + recipes[i].name + "</p><br>";
			newHtml += "<p class='stats'><img class='icon' src='../assets/recipes/fridgeicon.png'><b> (100 - " + recipes[i].percentNeed*100 + ")?round}% </b>recipe completed</p>";
			newHtml += "<p class='stats'><img class='icon' src='../assets/recipes/cart.png'><b>" + recipes[i].shoppingPrice + "</b> to complete recipe</p>";
			newHtml += "<p class='shopping-list'>Shopping List:</p>";
			newHtml += "<ul class='list-ingred'>";
			var shoppingList = "";
			for (var j = 0; j < recipes[i].shoppingList.length; j++) {
				shoppingList += "<li>" + recipes[i].shoppingList[j].name + "</li>"
			}
			newHtml += shoppingList + "</ul></td>";
		}
		$("#recipeHolder").html(newHtml);

		//display recipes here

	});
}
