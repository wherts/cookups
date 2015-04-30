$(document).ready(function () {
  $(".hoverli").hover(
	  function () {
	     $('ul.dropdown-menu').stop().slideDown('medium');
	  }, 
	  function () {
	     $('ul.dropdown-menu').stop().slideUp('medium');
	  }
	);
});