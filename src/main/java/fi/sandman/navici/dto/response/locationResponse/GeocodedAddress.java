package fi.sandman.navici.dto.response.locationResponse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.SearchableLocation;
import fi.sandman.navici.dto.request.locationRequest.Address;
import fi.sandman.navici.dto.response.routeRequest.Point;

@Root(name = "GeocodedAddress")
public class GeocodedAddress implements SearchableLocation {

	@Attribute
	private String locationType;
	@Attribute
	private Long locationId;
	@Element(name = "Address")
	private Address address;
	@Element(name = "Point")
	private Point point;

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCity() {
		if (address != null) {
			return address.getPlace().getValue();
		}
		return null;
	}

	public Integer getStreetNumber() {
		if (address != null) {
			return address.getStreetAddress().getStreetLocation();
		}
		return null;
	}

	public String getStreetName() {
		if (address != null) {
			return address.getStreetAddress().getStreet().getValue();
		}
		return null;
	}

	@Override
	public String toString() {
		if (getStreetNumber() != null) {
			return getStreetName() + " " + getStreetNumber() + ", " + getCity();
		} else {
			return getStreetName() + ", " + getCity();
		}
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
