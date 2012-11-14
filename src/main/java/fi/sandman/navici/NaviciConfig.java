package fi.sandman.navici;

import fi.sandman.navici.client.City;

/**
 * Represents configs found at http://{@link City}
 * .matkahuolto.info/fi/config.js.php
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public class NaviciConfig {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
