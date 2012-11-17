package fi.sandman.navici.dto.response.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.utils.coordinate.CoordinateConversionFailed;
import fi.sandman.utils.coordinate.CoordinateUtils;

/**
 * Represents a signle map point. Used to determine e.g. start and end points of
 * route.
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
@Root(name = "POINT")
public class Point extends Path {

	@Attribute(required = false)
	private String uid;
	@Attribute(name = "x", required = false)
	private Double longitude;
	@Attribute(name = "y", required = false)
	private Double latitude;
	@Element(name = "pos", required = false)
	private String pos;
	@Element(name = "ARRIVAL", required = false)
	private ArrivalDeparture arrival;
	@Element(name = "DEPARTURE", required = false)
	private ArrivalDeparture departure;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	@Override
	public List<PathPoint> getPathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		PathPoint pp = new PathPoint(latitude, longitude);

		try {
			pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));
		} catch (CoordinateConversionFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pathPoints.add(pp);
		return pathPoints;
	}

	@Override
	public PathType getPathType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
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

}
