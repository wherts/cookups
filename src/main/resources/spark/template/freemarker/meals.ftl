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
  <#include "/header.ftl">  
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
