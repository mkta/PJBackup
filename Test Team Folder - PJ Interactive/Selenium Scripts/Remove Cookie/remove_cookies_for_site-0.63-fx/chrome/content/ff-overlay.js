(function() {

	onFirefoxLoad = function(event) {
	  document.getElementById("contentAreaContextMenu")
			  .addEventListener("popupshowing", function (e){ showFirefoxContextMenu(e); }, false);
	};

	showFirefoxContextMenu = function(event) {
	  // show or hide the menuitem based on what the context menu is on
	  document.getElementById("context-removecookies").hidden = gContextMenu.isContentSelected;
	};
	window.addEventListener("load", onFirefoxLoad, false);
	console.log('rc loaded');
})();

