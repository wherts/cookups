<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/forms.css">
    <link rel="stylesheet" href="css/cookwfriends.css">
    <link rel="stylesheet" type="text/css" href="css/jquery.tokenize.css" />
  </head>
  <body>
    <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyArVp3KDIUpuzIfG_eDzQ4biYu29k15Cv8"></script>
    <script src="js/cookwfriends.js"></script>
    <script src="js/main.js"></script>
  <#include "/header.ftl">  
     <div class="block" id="title">
      <div class="container"><h1 class="h-text" id="title-text">cook with friends</h1></div>
     </div>
      <div class="block c">
      <div class="container">
      <div class="form-box inset">
         <div id="friends-form" method="POST">
         <p class="subheader-text">Make a Meal</p>
         <div id='form-contents'>
		  <div class='left half'>	
			  <div class="form-entry">Meal Name<br><input type="text" name="name"></div>
			  <div class="form-entry">Date<br><span id='date-label'><input type="date" id="datePicker" name="date" required></span></div>
	          <div class="form-entry">Time<br>   
	          		<input type="time" id="startTimePicker" name="time_start" value="19:30"> to <input type="time" id="endTimePicker" name="time_end">
	          </div>
	          <div class="form-entry">Add chefs<br><select name="chefs" id="add-chefs" multiple="multiple" class="tokenize-sample"></select></div>
		  </div>
		  <div class='right half'>
          <div id='map-field'>Select a location for your meal<br><div id='map-container'></div></div>
          </div></div>
          <div class="btn-container"><input type='button' class="btn" id='makeCookupBtn' onClick=submitCookFriends() value="Create Meal"></div>
         </div>
      </div>
      <div id="mealLink"></div>
    </div>
   </div>
  </body>
</html>
