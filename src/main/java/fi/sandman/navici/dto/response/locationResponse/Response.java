package fi.sandman.navici.dto.response.locationResponse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Reponse")
public class Response {
	
	@Element(name = "LocationResponseList")
	private LocationResponse locationResponse;

	public LocationResponse getLocationResponse() {
		return locationResponse;
	}

	public void setLocationResponse(LocationResponse locationResponse) {
		this.locationResponse = locationResponse;
	}

}
