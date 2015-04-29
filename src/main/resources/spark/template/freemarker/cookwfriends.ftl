<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/cookwfriends.css">
    <link rel="stylesheet" type="text/css" href="css/jquery.tokenize.css" />
  </head>
  <body>
     <!-- HEADER -->
     <div class="block a">
        <div class="header container">
        <img class="left" src="assets/logosmall.png" />
        <div id="nav">
          <a class="header-text" id="logout" href="/logout">logout</a>
          <div id="search-bar">
            <input id="search-input" type="text" placeholder="Search" name="search"><img src="assets/search.png" id="search-icon" class="right" />
          </div>
          <div id="links">
            <a class="page-link header-text" href="/cook">Cookup</a>
            <a class="page-link header-text" href="/meals">Meals</a>
            <a class="page-link header-text" href="/browse">Browse</a>
            <a class="page-link header-text" href="/profile">Profile</a>
          </div>
        </div>
       </div>
     </div>
     <div class="block" id="title">
      <div class="container"><h1 class="h-text" id="title-text">cook with friends</h1></div>
     </div>
      <div class="block c">
      <div class="container">
      <div class="form-box inset">
         <form id="friends-form" method="POST">
		  <p class="subheader-text">Make a Meal</p>	
		  <div class="form-entry">Meal Name<br><input type="text" name="name"></div>
		  <div class="form-entry">Date<br>   <input type="date" name="date"></div>
          <div class="form-entry">Time<br>   
          		<input type="time" name="time_start"> to <input type="time" name="time_end">
          </div>
          <div class="form-entry">Add chefs<br><select id="add-chefs" multiple="multiple" class="tokenize-sample"></select></div>
          <div class="btn-container"><input class="btn" type="submit" value="Create Meal"></div>
         </form>
      </div>
    </div>
   </div>
    <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/forms.js"></script>
  </body>
</html>
