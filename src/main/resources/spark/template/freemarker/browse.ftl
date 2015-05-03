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
  <#include "/header.ftl">  
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
					    <#assign link = "../assets/recipes/" + recipePics[x] + ".jpg">
						<td><a class="recipe" href="${recipes[x]}"><div class='img-container'><img class="recipe" src="${link}"></div><span class="recipe-name">${x}</span></a></td>
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
