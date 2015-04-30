<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
        <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login.css">
  </head>
  <body>
      <script src="js/jquery-2.1.1.js"></script>
	  <script src="js/jquery.tokenize.js"></script>
      <script src="js/forms.js"></script>
     <div class="block a">
  	   <div class="containter" id="head"><img id="logo" src="assets/logo.png" /></div>
     </div><div class="block" id="main-login">
      <div class="container">
  	 	<div class="form-box left">
        <form action="/login" method="POST">
  	 		<h1 class="h-text form-title">cookups expert?</h1>
          <div class="input-group">
            <div class="field">
              <h3 class="field-label h-text">email:</h3>
              <input class="input" type="text" name="id"><br>
            </div>
            <div class="field">
              <h3 class="field-label h-text">password:</h3>
              <input class="input" type="password" name="password"><br>
            </div>
          </div>
          <input class="login-btn btn h-text" type="submit" value="login">
        </form>
  	 	</div>
  	 	<div class="form-box right">
          <form name="signup-form" action="/signup" onsubmit="return validateSignup()" method="POST">
  	 	  	<h1 class="h-text form-title">new here?</h1>
  	 	  	<div class="field">
              <h3 class="field-label h-text">name:</h3>
              <input class="input" type="text" name="name"><br>
            </div>
            <div class="field">
              <h3 class="field-label h-text">email:</h3>
              <input class="input" type="text" name="email"><br>
            </div>
            <div class="field">
              <h3 class="field-label h-text">password:</h3>
              <input class="input" type="password" name="password"><br>
            </div>
            <input class="login-btn btn h-text" type="submit" value="Sign Up">
          </form>
        </div>
  	 	</div>
     </div>
     <div class="block b">
       <div class="container" id="about">
          <h1 class="h-text">about brown cookups</h1>
          <p class="about-text p-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.<br><br>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem. Investigationes demonstraverunt lectores legere me lius quod ii legunt saepius. Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas humanitatis per seacula quarta decima et quinta decima. Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum.</p>
       </div>
     </div>
  </body>
</html>
