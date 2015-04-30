<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/html5bp.css">
    <link rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" type="text/css" href="../css/jquery.tokenize.css" />
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/profile.css">
  </head>
  <body>
     <!-- HEADER -->
     <div class="block a">
        <div class="header container">
        <img class="left" src="../assets/logosmall.png" />
        <div id="nav">
          <a class="header-text" id="logout" href="/logout">logout</a>
          <div id="search-bar">
            <input id="search-input" type="text" placeholder="Search" name="search"><img src="../assets/search.png" id="search-icon" class="right" />
          </div>
          <div id="links">
            <a class="page-link header-text" href="/cook">Cookup</a>
            <a class="page-link header-text" href="/meals">Meals</a>
            <a class="page-link header-text" href="/browse">Browse</a>
            <a class="page-link header-text" href=${profLink}>Profile</a>
          </div>
        </div>
       </div>
     </div>
  <div class="block b">
      <div class="left half about">
      	  <div id="fridge-container"><img class="profile-pic" src="../assets/sample-pic.png"></div>
	      <div class="about-me">
	        <p class="name h-text">${name}</p>
	        <p class="subheader h-text">
	        	<#if editable == true>
	        		Favorite Recipes:<br>
	        		<select id="fav-recipes" multiple="multiple" class="tokenize-sample"></select>
	        	<#else>
	        		Favorite Recipes:
	        		<#list favRecipes as x>
	        		${x}
	        		</#list>
	        	</#if>
	        </p>
	      </div>
	  </div>
      <div class="right half ingred">
      <p class="title h-text">${name}'s Ingredients</p>
      <div id="fridge">
        <#if editable == true>
    		<select id="curr-ingredients" multiple="multiple" class="tokenize-sample ingredient-box"></select>
    	<#else>
		    <#list personIngredients as x>
    		${x}
    		</#list>
    	</#if>
      </div>
  </div>
     <script src="../js/jquery-2.1.1.js"></script>
	<script src="../js/jquery.tokenize.js"></script>
    <script src="../js/forms.js"></script>
    <script src="../js/profile.js"></script>
  </body>
</html>
