package fi.sandman.navici.dto.response.routeRequest;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Represents a bus stop
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 *
 */
@Root(name = "STOP")
public class Stop {

	@Attribute(name = "x")
	private Double longitude;
	@Attribute(name = "y")
	private Double latitude;
	@Element(name = "ARRIVAL")
	private ArrivalDeparture arrival;
	@Element(name = "DEPARTURE")
	private ArrivalDeparture departure;
	@ElementList(name = "NAME", inline = true)
	private List<Name> name;
	@Element(name = "XTRA")
	private Xtra xtra;

	
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

	public Xtra getXtra() {
		return xtra;
	}

	public void setXtra(Xtra xtra) {
		this.xtra = xtra;
	}

	public List<Name> getName() {
		return name;
	}

	public void setName(List<Name> name) {
		this.name = name;
	}

}
