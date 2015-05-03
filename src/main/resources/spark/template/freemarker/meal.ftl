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
			<#assign profilePic = "/assets/people/" + x[0] + ".jpg">
			<p><img class="profile-pic-icon" src="${profilePic}"><a href="${p.url()}">${p.name()}</a></p>
		</#list>
      </div>
  </div>
  <div class="block b last-div">
      <div id="recipe-suggestions" class="container">
      <p class="meal-subtitle">Recipe Suggestions</p>
          <div id="sortAndFilter">
         <div class="slider">
            <h3>Filter by:</h3>
            <label for="amount">Price range:</label>
            <div id="priceSlider" class="slider-range"></div>
            <input type="text" id="priceAmount" class="amount" readonly>
        </div>
      <div class="slider" id="percentSliderHolder">
            <label for="amount">Percentage of ingredients owned:</label>
            <div id="percentSlider" class="slider-range"></div>
            <input type="text" id="percentAmount" class="amount" readonly>
      </div>
      
      <br>
      <h3>Sort by:</h3>
      <div id="sortType" value="default">
        <input type="button" class="btn" value="Least Missing Ingredients"><a href="/meal/"+meal.url()+"/fewest-missing"></a></input>
        <input type="button" class="btn" value="Shopping Costs"><a href="/meal/"+meal.url()+"/price-asc"></a></input>
        <input type="button" class="btn" value="Meal Fanciness"><a href="/meal/"+meal.url()+"/fancy-asc"></a></input>
      </div>
        <input id="reverseButton" onclick="reverseRecipes()" type="button" class="btn" value="Reverse Order"></input>
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
				<#setting number_format="0.##">
				<p class='stats'><img class='icon' src='/assets/recipes/cart.png'><b>$${r.shoppingPrice()}</b> to complete recipe</p>
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
