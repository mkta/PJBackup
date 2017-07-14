
Selenium.prototype.doAaa = function cleanThisSiteCookies() {
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