var priceLow;
var priceHigh;
var percentLow;
var percentHigh;
var mealPath;
var map;

$(document).ready(function() {
		mealPath = window.location.pathname;
		$( "#priceSlider" ).slider({
			range: true,
			min: 0,
			max: 250,
			values: [ 0, 250 ],
			slide: function( event, ui ) {
				priceLow = ui.values[ 0 ];
				priceHigh = ui.values[ 1 ];
				$( "#priceAmount" ).val( "$" + priceLow + " - $" + priceHigh );
				reloadInRange();
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
				percentLow = ui.values[ 0 ];
				percentHigh = ui.values[ 1 ];
				$( "#percentAmount" ).val(percentLow + "% - " + percentHigh + "%");
				reloadInRange();
			}
		});
		percentLow = $( "#percentSlider" ).slider( "values", 0 );
		percentHigh = $( "#percentSlider" ).slider( "values", 1 );
		$( "#percentAmount" ).val(percentLow + "% - " + percentHigh + "%");
		reloadInRange();
		if ($('#map-container').length) {
			initMap();
		}
	}); //ends ready

function initMap() {
  var mapOptions = {
    zoom: 14
  };
  map = new google.maps.Map(document.getElementById('map-container'), mapOptions);
  var latlng = document.getElementById('latlng').value.split(",");
  console.log("point: " + latlng[0] + "," + latlng[1]);
  var point = new google.maps.LatLng(latlng[0], latlng[1]);
  map.setCenter(point);
  var mealLocation = new google.maps.Marker({
		position: point,
		clickable: false,
		map: map,
  });
}

function reloadInRange() {
	var recipes = $(".allRecipes table td");
	var numbers = $(".allRecipes table tr b");
	console.log(recipes.length);
	console.log(numbers.length);
	for (var i=0; i<numbers.length;i+=2) {

		var percent = numbers[i].innerHTML.trim();
		percent = percent.substring(0, percent.length - 1);
		percent = parseFloat(percent);

		var price = numbers[i+1].innerHTML.trim();
		price = price.substring(1, price.length);
		price = parseFloat(price);

		if (percent > percentHigh || percent < percentLow || price > priceHigh || price < priceLow) {
			recipes[i/2].hidden = true;
		}
		else {
			recipes[i/2].hidden = false;
		}
	}
}

function reverseRecipes() {
	$(".allRecipes table tr").html($(".allRecipes tr td").get().reverse());
}
