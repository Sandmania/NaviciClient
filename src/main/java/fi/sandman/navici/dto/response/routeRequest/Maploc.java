package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.DTOObject;

@Root(name = "MAPLOC")
public class Maploc implements DTOObject {

	@Attribute(name = "x")
	private Double longitude;
	@Attribute(name = "y")
	private Double latitude;
	@Attribute
	private String type;
	@Element(name = "ARRIVAL")
	private ArrivalDeparture arrival;
	@Element(name = "DEPARTURE")
	private ArrivalDeparture departure;
	@Element(name = "NAME", required = false)
	private Name name;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrivalDeparture getArrival() {
		return arrival;
	}

	public void setArrival(ArrivalDeparture arrival) {
		this.arrival = arrival;
	}

	public ArrivalDeparture getDeparture() {
		return departure;
	}

	public void setDeparture(ArrivalDeparture departure) {
		this.departure = departure;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

}
