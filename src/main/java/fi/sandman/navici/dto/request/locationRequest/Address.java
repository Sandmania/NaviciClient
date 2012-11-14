package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Addres")
public class Address {

	@Element(name = "Place")
	private Place place;
	@Element(name = "StreetAddress", required = false)
	private StreetAddress streetAddress;

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public StreetAddress getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}

}