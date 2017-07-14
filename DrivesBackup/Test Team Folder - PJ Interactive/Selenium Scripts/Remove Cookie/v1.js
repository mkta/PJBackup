
Selenium.prototype.doClearCookies2 = (function() {
	function endsWith(string, text) {
		var pos = string.indexOf(text);
		if (pos != -1 && string.length - pos == text.length) {
		return true;
		} else {
		return false;
		}
	}

	function beginsWith(string, text) {
		var pos = string.indexOf(text);
		if (pos == 0) {
		return true;
		} else {
		return false;
		}
	}

	function cleanThisSiteCookies() {
		var cookieManager = Components.classes["@mozilla.org/cookiemanager;1"].getService(Components.interfaces.nsICookieManager);

		// Get the URL, and convert it to UPPERCASE for easy matching
		var urlbar = document.getElementById("urlbar");
		var current_url = urlbar.value;
		if(!current_url) 
			return;
		current_url = current_url.toUpperCase()
		

		// Add a "." before the URL, as some cookies are stored as .www.domain.com
		if(beginsWith(current_url, "HTTP://") && current_url.length>7) {
			//alert("beginswith 1 true " + current_url);
			var s=current_url;
			current_url = s.substring(0, 7) + "." + s.substring(7);
		} else 	if(beginsWith(current_url, "HTTPS://") && current_url.length>8) {
				//alert("beginswith 1 true " + current_url);
				var s=current_url;
				current_url = s.substring(0, 8) + "." + s.substring(8);
		}

		//alert(current_url);
		var iter = cookieManager.enumerator;
		var cookie_count = 0;
		while (iter.hasMoreElements()) {
			var cookie = iter.getNext();
			if (cookie instanceof Components.interfaces.nsICookie) {
				//alert(current_url + " instanceOf " + cookie.host + current_url.indexOf(cookie.host));
				if (current_url.indexOf(cookie.host.toUpperCase()) != -1) {
					cookieManager.remove(cookie.host, cookie.name, cookie.path, cookie.blocked);
					cookie_count++;
				}
			}
		}
		setStatusMessage(cookie_count + " cookie(s) removed", 1000*3);
	}


	function setStatusMessage(msg, timeToClear) {
		//statusBar = document.getElementById("statusbar-display");
		statusBar = gBrowser.getStatusPanel()
		if (!statusBar) return;

		var oldmsg = statusBar.label;
		statusBar.label = msg;
		if(timeToClear) setTimeout( function() { setStatusMessage(oldmsg, 0); }, timeToClear);
	}     

	function show_alert(title, message) {
	   var promptService = Components.classes["@mozilla.org/embedcomp/prompt-service;1"]
									  .getService(Components.interfaces.nsIPromptService);
	   promptService.alert(window, title, message);
	}

	var listeners = {
		onCommand: function(e) {
			cleanThisSiteCookies();
		}
	};

	var namespace=function(c,f,b){var e=c.split(f||"."),g=b||window,d,a;for(d=0,a=e.length;d<a;d++){g=g[e[d]]=g[e[d]]||{}}return g};
	namespace("com.dwipal.removecookies").listeners = listeners;
	
})();
