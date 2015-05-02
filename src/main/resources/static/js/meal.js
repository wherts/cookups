var priceLow;
var priceHigh;
var percentLow;
var percentHigh;

$(document).ready(function() {
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
	});

