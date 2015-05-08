var map;
var currPos;
var mealLocation;

Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

Date.prototype.toTimeInputValue = (function() {
    var local = new Date(this);
    local.setHours(this.getHours() + 2);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(11,16);
});

Date.prototype.toEndTimeInputValue = (function() {
    var local = new Date(this);
    local.setHours(this.getHours() + 4);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(11,16);
});

$(document).ready(function () {
	$("#viewCookupBtn").hide(); //hide cookup button
	document.getElementById('datePicker').value = new Date().toDateInputValue();
	document.getElementById('startTimePicker').value = new Date().toTimeInputValue();
	document.getElementById('endTimePicker').value = new Date().toEndTimeInputValue();
	initMap();
});


$.get("/allUsers", function(JSONresponse) {
	var response = JSON.parse(JSONresponse);
	var names = response.names;
	var ids = response.ids;
	var options = "";
	
	for (var i=1; i<=names.length; i++) {
		if (ids[i-1] != $("#kitchen-link").attr("href").split("/")[2] + "@brown.edu") {
			options += "<option value='"+i+"'>"+names[i-1]+" ("+ids[i-1]+")</span></option>";
		}
	}
	$("#add-chefs").html(options);
	$("#add-chefs").tokenize({
			newElements: false,
	});

});

function validateForm(chefs) {
	var mealName = $("#friends-form input[name=name]").val();
	var date = $('#friends-form input[name=date]').val();
	var startTime = $('#friends-form input[name=time_start]').val();
	var endTime = $('#friends-form input[name=time_end]').val();
	
	$("#friends-form input[name=name]").removeAttr("style");
	$("#friends-form input[name=date]").removeAttr("style");
	$("#friends-form input[name=time_start]").removeAttr("style");
	$("#friends-form input[name=time_end]").removeAttr("style");
	$('.TokensContainer').removeAttr("style");

	if (mealName == "" || date == "" || startTime == "" || chefs == "") {
		if (mealName == "") { $("#friends-form input[name=name]").attr("style", "border:5px solid red");}
		if (date == "") {$("#friends-form input[name=date]").attr("style", "border:5px solid red");}
		if (startTime == "") {$('#friends-form input[name=time_start]').attr("style", "border:5px solid red");}
		if (chefs == "") {$('.TokensContainer').attr("style", "border:5px solid red");}
		return false;
	}
	return true;
}

function submitCookFriends() {
	var chefs="";
	var delim=",";
    $( "select option:selected" ).each(function() {
    	var regex = /\(([^)]+)\)/;
    	var text = regex.exec($(this).text())[1];
    	console.log(text);
        chefs += text+delim;
    });
    
	if (!validateForm(chefs)) {
		return;
	}
	
    var date = $('#friends-form input[name=date]').val();
    var today = new Date().toDateInputValue();

    if (date < today) {
    	console.log('error');
    	$('#date-label').after("<p style='color:red; margin:0'>Please enter a date in the future.</p>");
    } else {
    	var location;
    	if (mealLocation == null) {
    		location = null;
    	} else {
			var latLng = mealLocation.getPosition();
			
			var latitude = latLng.lat();
			var longitude = latLng.lng();
    		location = latitude + "," + longitude;
    	}
    	console.log("location " + location);
    	
		var params = {
			name: $('#friends-form input[name=name]').val(),
			date: date,
			timeStart:  $('#friends-form input[name=time_start]').val(),
			timeEnd:  $('#friends-form input[name=time_end]').val(),
			chefs: chefs,
			location: location,
		}
		
		console.log(params);
		
		$.post("/makemeal", params, function(responseJSON) {
			var url = window.location.href;
			var path = window.location.pathname;
			var prefix = url.replace(path, "");
			var mealLink = prefix + JSON.parse(responseJSON);
			window.location.replace(mealLink);
		});
	}
}

function initMap() {
  var mapOptions = {
    zoom: 16
  };
  map = new google.maps.Map(document.getElementById('map-container'), mapOptions);

  // Try HTML5 geolocation
  if(navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	  
	  currPos = new google.maps.Circle({
	  	map: map,
	  	center: pos,
	  	clickable: false,
	  	radius: 10,
	  	strokeColor: "#2e7bee",
	  	strokeOpacity: 0.4,
	  	fillColor: "#2e7bee",
	  	fillOpacity: 1,
	  	strokeWeight: 15,
	  });
      map.setCenter(pos);
    }, function() {
      handleNoGeolocation(true);
    });
  } else {
    // Browser doesn't support Geolocation
    handleNoGeolocation(false);
  }
  
  google.maps.event.addListener(map, 'click', function(e) {
    var point = new google.maps.LatLng(e.latLng.lat(), e.latLng.lng());
    
    if (mealLocation == null) {
    	mealLocation = new google.maps.Marker({
    		position: point,
    		clickable: false,
    		map: map,
    		
    	});
    } else {
    	mealLocation.setPosition(point);
    }
    
  });
}

function handleNoGeolocation(errorFlag) {
  if (errorFlag) {
    var content = 'Error: The Geolocation service failed.';
  } else {
    var content = 'Error: Your browser doesn\'t support geolocation.';
  }

  var options = {
    map: map,
    position: new google.maps.LatLng(41.826206, -71.403273),
    content: content
  };

  var infowindow = new google.maps.InfoWindow(options);
  map.setCenter(options.position);
}
