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
        <img class="left" src="../assets/logosmall.png" />
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
	          Heterosexual<input type="radio" name="orientation" value="heterosexual">
	          Homosexual<input type="radio" name="orientation" value="homosexual">
	          Bisexual<input type="radio" name="orientation" value="bisexual">
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
    <script src="js/main.js"></script>

  </body>
</html>
