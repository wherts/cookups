<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/meal.css">
    <link rel="stylesheet" href="css/main.css">
  </head>
  <body>
     <!-- HEADER -->
     <div class="block a">
        <div class="header container">
        <a href='/cookup'><img class="left" src="../assets/logosmall.png" /></a>
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
   <div class="block c" id="title">
      <div class="container"><h1 class="meal-subtitle" id="title-text">Meals</h1><div>
     <div class="block c last-div">
     	<div class='container'>
     	<ul>
			<#list meals as x>
				<#assign url = '/meal/' + x.url() + '/default'>
				<a class='meal-list' href='${url}'><li>${x.name()}</li></a>
			</#list>
		</ul>
		</div>
     </div>
     </div>
     
     <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>
