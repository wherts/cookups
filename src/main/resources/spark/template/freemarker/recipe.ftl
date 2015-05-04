<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/html5bp.css">
    <link rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="../css/recipe.css">
  </head>
  <body>
  <#include "/header.ftl">  
     <div class='block b'>
     	<div class='container'>
 			<#assign link = "../assets/recipes/" + recipe.picPath()+".jpg">
 			<p class='title' id='recipe-title'>${recipe.name()}</p>
 			<div class='img-container'><img src='${link}' class='recipe-pic'></div>
     	</div>
     </div>
     <div class='block c'>
     	<div class='container'>
     		<table>
     			<tr>
      				<#setting number_format="0.##">
     				<td>Cost to completion: $${recipe.shoppingPrice()}</td>
     				<#setting number_format="">
     				<td class='table-right'>You need ${recipe.shoppingList()?size} ingredients to complete this recipe.</td>
     			</tr>
     		</table>
     	</div>
     </div>
     <div class='block a'>
     	<div class='container'>
     		<p class='recipe-subtitle'>Ingredients:</p>
     		<div id='ingredients'>
			 	 <#list recipe.ingredients() as x>
					<p class='ingredient'>
						<span>${x.ounces()} oz ${x.name()}</span>
						<#if recipe.shoppingList()?seq_contains(x)>
							<span class='need-to-buy'> (need to buy)</span>
						</#if>
					</p>
				 </#list>
			</div>
		</div>
	 </div>
	 <div class='block b last-div'>
	 	<div class='container'>
	 		<p class='recipe-subtitle' id='instructions-header'>Instructions: </p>
 			<div id='instructions'>
		     <#list recipe.instructions() as x>
				<p class='instruction'>${x}<br></p>
			 </#list>
			 </div>
		</div>
	</div>
     <script src="../js/jquery-2.1.1.js"></script>
	<script src="../js/jquery.tokenize.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/recipe.js"></script>
  </body>
</html>
