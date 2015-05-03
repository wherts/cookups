<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Brown Cookups</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/cookup.css">
    <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
	<script src="js/main.js"></script>
  </head>
  <body>
  <#include "/header.ftl">  
     <div class="block b last-div">
     	<div class="container">
     	<h1 class="title">Search Results:</h1>
     	<ul class="search-results">
	     	<#list people?keys as x>
				<li class="search-results"><a href="${people[x]}">${x}</a></li>
			</#list>
			<#list recipes?keys as x>
				<li class="search-results"><a href="${recipes[x]}">${x}</a></li>
			</#list>
		</ul>
		</div>
	 </div>
  </body>
</html>