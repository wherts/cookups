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
            <#assign profLink = "/people/"+path>
            <li><a class="page-link header-text" href=${profLink}>Profile</a></li> 
          </ul>
          <ul class="dropdown-menu hoverli">
	        <li><a href="/cookwfriends">Cook with Friends</a></li>
	        <li><a href="/cookup">CookUp</a></li>
		  </ul>
        </div> 
       </div>
     </div>
  <div class="block b last-div">
      <div class="left half about">
          <#assign picLink = "../assets/people/"+path+".jpg">
      	  <div id="fridge-container">
            <div class="profile-pic" style="background:url(${picLink})">
              <div id="overlay">
                <span id="edit">Change<br>your look</span>
                <input type="file" id="uploadPicture"></input>
              </div>
            </div></div>          
	      <div id="about" class="about-me">
	        <p class="name h-text">${name}</p>
	        <input id="updateButton" type="submit" value="Update Profile">
	        <p class="subheader h-text">
	        	<#if editable == true>
	        		Favorite Cuisines:<br>
	        		<select id="fav-cuisines" multiple="multiple" class="tokenize-sample"></select>
	        	<#else>
	        		Favorite Cuisines:
	        		<#list favCuisines as x>
	        		${x}
	        		</#list>
	        	</#if>
	        </p>
	      </div>
	  </div>
      <div class="right half ingred">
      <p class="title h-text">${name}'s Ingredients</p>
      
      <div id="ingredientInput">
        <#if editable == true>
    		<select id="curr-ingredients" multiple="multiple" class="tokenize-sample ingredient-box"></select>
    	<#else>
		    <#list personIngredients as x>
    		${x}
    		</#list>
    	</#if>
      </div>
          <div id="fridgeHolder">
                  <div id="fridge"></div>
          </div>
          <div id="pantryHolder">
            <div id="pantry"></div>
          </div>
      
  </div>
  
     <script src="../js/jquery-2.1.1.js"></script>
	<script src="../js/jquery.tokenize.js"></script>
    <script src="../js/profile.js"></script>
    <script src="../js/main.js"></script>
  </body>
</html>
