 <div class="block a">
    <div class="header container">
    <a href='/cookup'><img class="left" src="/assets/logosmall.png" /></a>
    <div id="nav">
      <div id='logout-container'><a class="header-text" id="logout" href="/logout">logout</a></div>
	  <form id="search-bar" action="/search" method="POST">
        <input id="search-input" type="text" placeholder="Search" name="term">
        <button type="submit" id="search-btn"><img src="/assets/search.png" id="search-icon" class="right" /></button>
      </form>
      <ul id="links">
        <li><a id="cook-link" class="page-link header-text hoverli" href="/cook">Cook</a></li>        
		<li><a class="page-link header-text" href="/meals">Meals</a></li>
        <li><a class="page-link header-text" href="/browse">Browse</a></li>
        <li><a id="kitchen-link" class="page-link header-text" href=${path}>My Kitchen</a></li> 
      </ul>
      <ul class="dropdown-menu hoverli">
        <li><a href="/cookwfriends">Cook with Friends</a></li>
        <li><a href="/cookup">cookup<img src="/assets/heart.png" style="float:right;padding-right:10px;" width="15px"/></a></li>
	  </ul>
    </div> 
   </div>
 </div>