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
    <link rel="stylesheet" href="../css/meal.css">
    <link rel="stylesheet" href="../css/jquery-ui.css">
    <script src="../js/jquery-2.1.1.js"></script>
    <script src="../js/jquery.tokenize.js"></script>
    <script src="../js/meal.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/jquery-ui.js"></script>
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
  <div class="block b">
      <div id="meal-details" class="container">
        <p class="title" id="meal-title">${meal.name()}</p>
      	<ul class="meal-headers left">
      		<li>Host</li>
      		<li>Date</li>
      		<li>Time</li>
		</ul>
		<ul class="meal-info">
			<li>${meal.host().name()}</li>
			<li>${meal.date()}</li>
			<li>
			     <#if meal.endTime()??>
				   ${meal.time()} to ${meal.endTime()}
				<#else>
				   ${meal.time()}
				</#if>
			</li>
		</ul>
      </div>
  </div>
  <div class="block a">
      <div id="meal-attendees" class="container">
        <p class="meal-subtitle">Who's Attending</p>
		<#list meal.attending() as p>
			<#assign x = p.id()?split("@")>
			<#assign profilePic = "../assets/people/" + x[0] + ".jpg">
			<p><img class="profile-pic-icon" src="${profilePic}"><a href="${p.url()}">${p.name()}</a></p>
		</#list>
      </div>
  </div>
  <div class="block b last-div">
<<<<<<< HEAD
    <div id="sortAndFilter">
         <p>
            <label for="amount">Price range:</label>
            <input type="text" id="priceAmount" class="amount" readonly>
        </p>
      <div id="priceSlider" class="slider-range"></div>
      <p>
            <label for="amount">Percentage of ingredients owned:</label>
            <input type="text" id="percentAmount" class="amount" readonly>
      </p>
      <div id="percentSlider" class="slider-range"></div>
      <br>
      <span>Sort recipes by:</span>
      <select id="sortType" value="least-missing" onchange="changeSort()">
        <option value="least-missing">Least Missing Ingredients</option>
        <option value="price-asc">Shopping Costs ($-$$$)</option>
        <option value="price-des">Shopping Costs ($$$-$)</option>
        <option value="fancy-asc">Meal Fanciness ($-$$$)</option>
        <option value="fancy-des">Meal Fanciness ($$$-$)</option>
      </select>
    </div>
=======
>>>>>>> d4416cc2ce44a3e8288c41d5703648c172c996e8
      <div id="recipe-suggestions" class="container">
      <p class="meal-subtitle">Recipe Suggestions</p>
      <div id="sortAndFilter">
	         <p>
	            <label for="amount">Price range:</label>
	            <input type="text" id="priceAmount" class="amount" readonly>
	        </p>
	      <div id="priceSlider" class="slider-range"></div>
	      <p>
	            <label for="amount">Percentage of ingredients owned:</label>
	            <input type="text" id="percentAmount" class="amount" readonly>
	      </p>
	      <div id="percentSlider" class="slider-range"></div>
	      <br>
	      <span>Sort recipes by:</span>
	      <select id="sortType">
	
	        <option value="least-missing">Least Missing Ingredients</option>
	        <option value="price-asc">Shopping Costs ($-$$$)</option>
	        <option value="price-des">Shopping Costs ($$$-$)</option>
	        <option value="fancy-asc">Meal Fanciness ($-$$$)</option>
	        <option value="fancy-des">Meal Fanciness ($$$-$)</option>
	      </select>
   	  </div>
      <div class="container">
        <div id="sliding suggestions">
        <table><tr>
<<<<<<< HEAD
	        <div id="recipeHolder">
		      	<#list recipes as r>
		      		<#assign link = "../assets/recipes/" + r.picPath()+".jpg">
		      		<td>
		      			<div class='recipe-pic-container'><img class='recipe-pic' src='${link}'></div>
		      			<p class='recipe-title'>${r.name()}</p><br>
						<p class='stats'><img class='icon' src='../assets/recipes/fridgeicon.png'><b> ${(100 - r.percentNeed()*100)?round}% </b>recipe completed</p>
						<p class='stats'><img class='icon' src='../assets/recipes/cart.png'><b>$${r.shoppingPrice()}</b> to complete recipe</p>
						<p class='shopping-list'>Shopping List:</p>
						<ul class='list-ingred'>
						<#list r.shoppingList() as i>
							<li>${i.name()}</li>
						</#list>
						</ul>
					</td>
				</#list>
			</div>
=======
      	<#list recipes.sortBy(sortType) as r>
      		<#assign link = "../assets/recipes/" + r.picPath()+".jpg">
      		<td><a href="${r.url()}">
      			<div class='recipe-pic-container'><img class='recipe-pic' src='${link}'></div>
      			<p class='recipe-title'>${r.name()}</p><br>
				<p class='stats'><img class='icon' src='../assets/recipes/fridgeicon.png'><b> ${(100 - r.percentNeed()*100)?round}% </b>recipe completed</p>
				<#setting number_format="0.##">
				<p class='stats'><img class='icon' src='../assets/recipes/cart.png'><b>$${r.shoppingPrice()}</b> to complete recipe</p>
				<p class='shopping-list'>Shopping List:</p>
				<ul class='list-ingred'>
				<#list r.shoppingList() as i>
					<li>${i.name()}</li>
				</#list>
				</ul>
			</a></td>
		</#list>
>>>>>>> d4416cc2ce44a3e8288c41d5703648c172c996e8
		</tr></table>
		</div>
      </div>
      </div>
  </div>
  </body>
</html>
