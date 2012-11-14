package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public @Root(name = "StreetAddress")
class StreetAddress {

	@Element(name = "Street")
	private Street street;
	@Element(name = "StreetLocation", required = false)
	private Integer streetLocation;
	
	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Integer getStreetLocation() {
		return streetLocation;
	}

	public void setStreetLocation(Integer streetLocation) {
		this.streetLocation = streetLocation;
	}

}