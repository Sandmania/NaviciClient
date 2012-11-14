package fi.sandman.navici.dto.response.locationResponse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.SearchableLocation;
import fi.sandman.navici.dto.request.locationRequest.Address;
import fi.sandman.navici.dto.response.routeRequest.Point;

@Root(name = "POI")
public class Poi implements SearchableLocation {

	@Attribute
	private Long locationId;
	@Attribute
	private int poiClassId;
	@Attribute
	private String POIName;
	@Element(name = "Address")
	private Address address;
	@Element(name = "Point")
	private Point point;
	
	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public int getPoiClassId() {
		return poiClassId;
	}

	public void setPoiClassId(int poiClassId) {
		this.poiClassId = poiClassId;
	}

	public String getPOIName() {
		return POIName;
	}

	public void setPOIName(String pOIName) {
		POIName = pOIName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCity() {
		if(address != null) {
			return address.getPlace().getValue();
		}
		return null;
	}

	public Integer getStreetNumber() {
		return null;
	}

	public String getStreetName() {
		return POIName;
	}
	
	@Override
	public String toString() {
		return getStreetName() + ", " + getCity();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
