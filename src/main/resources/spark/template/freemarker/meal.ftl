<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/normalize.css">
    <link rel="stylesheet" href="/css/html5bp.css">
    <link rel="stylesheet" href="/css/stylesheet.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.tokenize.css" />
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/meal.css">
    <link rel="stylesheet" href="/css/jquery-ui.css">
    <script src="/js/jquery-2.1.1.js"></script>
    <script src="/js/jquery.tokenize.js"></script>
    <script src="/js/meal.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/jquery-ui.js"></script>
  </head>
  <body>
  <#include "/header.ftl">  
  <div class="block b">
  	  <div class='container'>
  	  	  <p class="title" id="meal-title">${meal.name()}</p>
	      <div id="meal-details" class="left half">
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
			<div id='whos-attending' class="right half">
			<ul class='meal-headers left'>
				<li>Who's Attending</li>
			</ul>
			<ul class='meal-info'>
				<li>
					<#list meal.attending() as p>
						<#assign x = p.id()?split("@")>
						<#assign profilePic = "/assets/people/" + x[0] + ".jpg">
						<p class='people-list'><img class="profile-pic-icon meal-attendees" src="${profilePic}"><a class='meal-attendees' href="${p.url()}">${p.name()}</a></p>
					</#list>
				</li>
			</ul>
			</div>
	      </div>
      </div>
  </div>
  <div class="block a last-div">
      <div id="recipe-suggestions" class="container">
      <p class="meal-subtitle">Recipe Suggestions</p>
          <div id="sortAndFilter">
          <h3 class='recipe-filter-subheader'>Filter by:</h3>
         <div class="slider">
            <label class="filter-subheader" for="amount">Price range:</label>
            <div id="priceSlider" class="slider-range"></div>
            <input type="text" id="priceAmount" class="amount" readonly>
        </div>
      <div class="slider" id="percentSliderHolder">
            <label class="filter-subheader" for="amount">Percentage of ingredients owned:</label>
            <div id="percentSlider" class="slider-range"></div>
            <input type="text" id="percentAmount" class="amount" readonly>
      </div>
      <h3 id='sort-header' class='recipe-filter-subheader'>Sort by:</h3>
      <div id="sortType" value="default">
      	<#assign leastMissing = "/meal/"+meal.url()+"/fewest-missing">
        <input type="button" class="sort-btn btn" value="Least Missing Ingredients" onClick="location.href='${leastMissing}'"></input>
      	<#assign shoppingCosts = "/meal/"+meal.url()+"/price-asc">
        <input type="button" class="sort-btn btn" value="Shopping Costs" onClick="location.href='${shoppingCosts}'"></input>
      	<#assign mealFanciness = "/meal/"+meal.url()+"/fancy-asc">
        <input type="button" class="sort-btn btn" value="Meal Fanciness" onClick="location.href='${mealFanciness}'"></input>
      </div>
        <input id="reverseButton" onclick="reverseRecipes()" type="button" class="sort-btn btn" value="Reverse Order"></input>
    </div>
      <div class="container">
        <div id="sliding suggestions" class="allRecipes">
        <table><tr>
      	<#list meal.sortRecipes(sortType) as r>
      		<#assign link = "/assets/recipes/" + r.picPath()+".jpg">
      		<td><a href="${r.url()}">
      			<div class='recipe-pic-container'><img class='recipe-pic' src='${link}'></div>
      			<p class='recipe-title'>${r.name()}</p><br>
				<p class='stats'><img class='icon' src='/assets/recipes/fridgeicon.png'><b> ${(100 - r.percentNeed()*100)?round}% </b>recipe completed</p>
				<p class='stats'><img class='icon' src='/assets/recipes/cart.png'><b>$#{r.shoppingPrice(); m2M2}</b> to complete recipe</p>
				<p class='shopping-list'>Shopping List:</p>
				<ul class='list-ingred'>
				<#list r.shoppingList() as i>
					<li>${i.name()}</li>
				</#list>
				</ul>
			</a></td>
		</#list>
		</tr></table>
		</div>
      </div>
      </div>
  </div>
  </body>
</html>
