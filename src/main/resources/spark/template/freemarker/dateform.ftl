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

    <div id="dateSignup">
      Romantic?<br>
      <input type="radio" name="type" value="romantic" checked>Yes
      <input type="radio" name="type" style="margin-left:20px;" value="platonic">No
      <br><br>
      <div id="romantic-opts">
        Gender<br>
        Male <input type="range" id="slider" onchange="sliderChange();" name="gender"> Female
        <br><br>
        Orientation (queer is not checking a box)<br>
        <input type="radio" name="orientation" value="homosexual">Homosexual
        <input type="radio" name="orientation" style="margin-left:20px;" value="heterosexual">Heterosexual
        <input type="radio" name="orientation" style="margin-left:20px;" value="bisexual">Bisexual
        <br>

        </div>
          <input id="cancel" onClick="closePopup()" class="btn" type="submit" value="Cancel">
          <input id="dateButton" onClick="submitCookup()" class="btn" type="submit" value="Find Matches">
         </div>
    </div>    

    <script src="../js/jquery-2.1.1.js"></script>
    <script src="../js/jquery.tokenize.js"></script>
    <script src="../js/profile.js"></script>
    <script src="../js/main.js"></script>
  </body>
</html>
