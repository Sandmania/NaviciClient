package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "LocationRequest")
public class LocationRequest {
	
	@Element(name = "FreeFormLocationName")
	private String freeFormLocationName;
	@Element(name = "Address")
	private Address address;
	

	public String getFreeFormLocationName() {
		return freeFormLocationName;
	}

	public void setFreeFormLocationName(String freeFormLocationName) {
		this.freeFormLocationName = freeFormLocationName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
