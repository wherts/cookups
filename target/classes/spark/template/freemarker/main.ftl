<!DOCTYPE html>
  <head>
        <link type="text/css" rel="stylesheet" href="css/main.css">
        <meta charset="utf-8">
        <title>${title}</title>
  </head>
  <body>
    <div id="header">
    <h2>Autocorrect</h2>
  </div>
    <div id="boxDiv">
      <textarea id="box" action="/autocorrect" placeholder="Start typing...">${content}</textarea>
    
    <form method="POST" action="/suggestions">
      <div id="suggestionBox">
          <ul id="list">
          </ul>
      </div>
    </form> 
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>
