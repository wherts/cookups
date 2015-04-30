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
    <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/cookwfriends.js"></script>
    <script src="js/main.js"></script>
     <!-- HEADER -->
     <div class="block a">
        <div class="header container">
        <img class="left" src="../assets/logosmall.png" />
        <div id="nav">
          <a class="header-text" id="logout" href="/logout">logout</a>
		  <form id="search-bar" action="/search" method="POST">
            <input id="search-input" type="text" placeholder="Search" name="term">
            <button type="submit" id="search-btn"><img src="../assets/search.png" id="search-icon" class="right" /></button>
          </form>
          <ul id="links">
            <li><a id="cook-link" class="page-link header-text hoverli" href="/cook">Cook</a></li>        
			<li><a class="page-link header-text" href="/meals">Meals</a></li>
            <li><a class="page-link header-text" href="/browse">Browse</a></li>
            <li><a class="page-link header-text" href=${profLink}>Profile</a></li> 
          </ul>
          <ul class="dropdown-menu hoverli">
	        <li><a href="/cookwfriends">Cook with Friends</a></li>
	        <li><a href="/cookup">CookUp</a></li>
		  </ul>
        </div> 
       </div>
     </div>
     <div class="block" id="title">
      <div class="container"><h1 class="h-text" id="title-text">cook with friends</h1></div>
     </div>
      <div class="block c">
      <div class="container">
      <div class="form-box inset">
         <div id="friends-form" method="POST">
		  <p class="subheader-text">Make a Meal</p>	
		  <div class="form-entry">Meal Name<br><input type="text" name="name"></div>
		  <div class="form-entry">Date<br>   <input type="date" name="date"></div>
          <div class="form-entry">Time<br>   
          		<input type="time" name="time_start"> to <input type="time" name="time_end">
          </div>
          <div class="form-entry">Add chefs<br><select name="chefs" id="add-chefs" multiple="multiple" class="tokenize-sample"></select></div>
          <div class="btn-container"><input class="btn" onClick=submitCookFriends() value="Create Meal"></div>
         </div>
      </div>
    </div>
   </div>
  </body>
</html>
