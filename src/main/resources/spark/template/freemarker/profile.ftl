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
    <script src="../js/jquery-2.1.1.js"></script>
    <script src="../js/jquery.tokenize.js"></script>
    <script src="../js/profile.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/jquery.lightbox_me.js"></script>
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
              <div id="btnContainer">
               <input id="dateButton" class="btn" onclick="showPopup()" type="submit" value="Update Dating Preferences">
               <input id="updateButton" class="btn" type="submit" value="Update Kitchen">
              </div>
               <div id="foodler-container"></div>
           </#if>
        </p>
      </div>
    </div>

    <div id="dateSignup" style="display:none">
      Romantic?<br>
      <#if wrapper.platonic() == false>
        <input type="radio" name="type" value="romantic" checked>Yes
        <input type="radio" name="type" style="margin-left:20px;" value="platonic">No
      <#else>
        <input type="radio" name="type" value="romantic">Yes
        <input type="radio" name="type" style="margin-left:20px;" value="platonic" checked>No
      </#if>
      <br><br>
      <div id="romantic-opts">
        Gender<br>
        Male <input type="range" id="slider" onchange="sliderChange();" name="gender" value="${wrapper.gender()}"> Female
        <br><br>
        Orientation (queer is not checking a box)<br>
        <input id="gay" type="radio" name="orientation" value="homosexual">Homosexual
        <input id="straight" type="radio" name="orientation" style="margin-left:20px;" value="heterosexual">Heterosexual
        <input id="bi" type="radio" name="orientation" style="margin-left:20px;" value="bisexual">Bisexual
        <br>
        <script type="text/javascript">
          <#if wrapper.queer() == false>
            <#if wrapper.bi() == true>
              turnOnOrientation("bi");
            <#elseif wrapper.gay() == true>
              turnOnOrientation("gay");
            <#else>
              turnOnOrientation("straight");
            </#if>
          </#if>
        </script>
    </div>    
    <div id="btn_container">
      <input id="cancel" onClick="closePopup()" class="btn" type="submit" value="Cancel">
      <input id="updateDate" onClick="submitCookup()" class="btn" type="submit" value="Update">
    </div>

  </body>
</html>
