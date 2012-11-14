package fi.sandman.navici.dto.response.locationResponse;

import java.util.List;

import fi.sandman.navici.dto.request.routeRequest.Location;

/**
{"key":"keskustie 23",
"data":
	{"locations":
		[{"category":"address",
		"x":"3434867.250000",
		"y":"6904655.000000",
		"name":"Keskustie",
		"city":"Jyv\u00e4skyl\u00e4",
		"number":23}]},
"status":0}
 * 
 * @author sandman
 *
 */
public class JsonLocationResponse {
	
	private String key;
	
	public static class Data {
		
		private List<Location> locations;

		public List<Location> getLocations() {
			return locations;
		}

		public void setLocations(List<Location> locations) {
			this.locations = locations;
		}
	}
	
	private int status;
	
	private Data data;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
