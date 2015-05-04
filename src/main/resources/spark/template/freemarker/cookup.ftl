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
    <link rel="stylesheet" href="css/cookup.css">
     <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/cookup.js"></script>
    <script src="js/main.js"></script>
  </head>
  <body>
  <#include "/header.ftl">  
     <div class="block" id="title">
      <div class="container"><h1 class="h-text" id="title-text">cookup</h1></div>
     </div>
      <div class="block c">
      <div class="container">
      <div class="form-box left">
         <div id="cookup-form">
          <p class="subheader-text">Make a Meal</p>	
          Romantic? <br>
          <input type="radio" name="type" value="romantic" checked>Yes
          <input type="radio" name="type" style="margin-left:20px;" value="platonic">No
          <br><br>
          <div id="romantic-opts">
	          Gender<br>
	          Male <input type="range" id="slider" onchange="sliderChange();" name="gender"> Female
	          <br><br>
	          Orientation (queer is not checking a box)<br>
            <input type="radio" name="orientation" value="homosexual">Homosexual
	          <input type="radio" name="orientation" style="margin-left:20px;" value="heterosexual">Heterosexual
	          <input type="radio" name="orientation" style="margin-left:20px;" value="bisexual">Bisexual
	          <br>
	      </div>
          <input id="cookupButton" onClick="submitCookup()" class="btn" type="submit" value="Find Matches">
         </div>
      </div>
      <div class="form-box half" id="matches">
      </div>
      <div id="mealLink"></div>
    </div>
   </div>


  </body>
</html>
