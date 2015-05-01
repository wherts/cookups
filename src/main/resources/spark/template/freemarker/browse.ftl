<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/browse.css">
  </head>
  <body>
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
   <div class="block d" id="title">
      <div class="container"><h1 class="h-text" id="title-text">Browse Recipes</h1></div>
     </div>
     <div class="block d">
     	<div class="container">
   		<div class="container">
     		<table class="recipe">
				<#list recipes?keys?chunk(5) as row>
				<tr>
					<#list row as x>
						<td><a class="recipe" href="${recipes[x]}"><div class='img-container'><img class="recipe" src="../assets/recipes/flank-steak.jpg"></div><span class="recipe-name">${x}</span></a></td>
					</#list>
				</tr>
			</#list>
		</table>
		</div>
		</div>
     </div>
     <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>
