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
  <#include "/header.ftl">  
  <div class="block" id="title"><h1 id="title-text" class="h-text">${name}'s kitchen</h1></div>
  <#assign picLink = "../assets/people/"+path+".jpg">
  <div id="picture-container">
    <div id="profPic" class="profile-pic" style="background:url(${picLink}),url(../assets/people/sample-pic.png); background-size: cover !important;background-position: center;">
        <#if editable == true>
          <div id="overlay">
            <span id="edit">Change<br>your look</span>
            <input type="file" id="uploadPicture" accept="image/*"></input>
          </div>
        </#if>
    </div>
    </div>




    <div class="left half ingred">
    <div id="fridgeHolder"><div id="fridge"></div></div>
    <div id="pantryHolder"><div id="pantry"></div></div>
    <#if editable == true>
           <div id="ingredientInput">
           <select id="curr-ingredients" multiple="multiple" class="tokenize-sample ingredient-box"></select>
           </div>
    <#else>
          <div id="ingredientInput" style="display:none">
          <#list personIngredients as x>
            <span hidden='true' value="${x.name()}">${x.name()}, ${x.ounces()} oz</span>
          </#list>             
          </div>
    </#if>

  </div>
    <div class="right half about">
                  <div id="about" class="about-me">
        <p class="subheader h-text">
          <#if editable == true>
            Favorite Cuisines:<br>
            <select id="fav-cuisines" multiple="multiple" class="tokenize-sample"></select>
          <#else>
            <span class="name h-text">Favorite Cuisines:</span>
             <#list favCuisines as x>
                 <br><span style="margin-left:35px; font-size:20px;">${x}</span>
             </#list>
          </#if>
           <#if editable == true>
                <br>
               <input id="updateButton" class="btn" type="submit" value="Update Kitchen">
           </#if>
        </p>
      </div>
        </div>
   <script src="../js/jquery-2.1.1.js"></script>
	<script src="../js/jquery.tokenize.js"></script>
    <script src="../js/profile.js"></script>
    <script src="../js/main.js"></script>
  </body>
</html>
