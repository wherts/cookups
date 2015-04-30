<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/cookup.css">
  </head>
  <body>
     <!-- HEADER -->
     <div class="block a">
        <div class="header container">
        <img class="left" src="assets/logosmall.png" />
        <div id="nav">
          <a class="header-text" id="logout" href="/logout">logout</a>
          <div id="search-bar">
            <input id="search-input" type="text" placeholder="Search" name="search"><img src="assets/search.png" id="search-icon" class="right" />
          </div>
          <div id="links">
            <a class="page-link header-text" href="/cook">Cookup</a>
            <a class="page-link header-text" href="/meals">Meals</a>
            <a class="page-link header-text" href="/browse">Browse</a>
            <a class="page-link header-text" href=${profLink}>Profile</a>
          </div>
        </div>
       </div>
     </div>
     <div class="block" id="title">
      <div class="container"><h1 class="h-text" id="title-text">cookup</h1></div>
     </div>
      <div class="block c">
      <div class="container">
      <div class="form-box">
         <form id="cookup-form" method="POST">
          <p class="subheader-text">Make a Meal</p>	
          Romantic? <br>
          <input type="radio" name="type" value="true" checked>Yes
          <input type="radio" name="type" value="false">No
          <br><br>
          <div id="romantic-opts">
	          Gender<br>
	          Male <input type="range" name="gender"> Female
	          <br><br>
	          Orientation (select none for queer)<br>  
	          Heterosexual<input type="checkbox" name="orientation" value="Heterosexual">
	          Homosexual<input type="checkbox" name="orientation" value="homosexual">
	          Bisexual<input type="checkbox" name="orientation" value="bisexual">
	          <br>
	      </div>
          <input class="btn" type="submit" value="Cook Up">
         </form>
      </div>
    </div>
   </div>
     <script src="js/jquery-2.1.1.js"></script>
	<script src="js/jquery.tokenize.js"></script>
    <script src="js/forms.js"></script>
  </body>
</html>
