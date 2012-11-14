package fi.sandman.navici.dto.response.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import fi.sandman.utils.coordinate.CoordinateConversionFailed;
import fi.sandman.utils.coordinate.CoordinatePoint;
import fi.sandman.utils.coordinate.CoordinateUtils;

/**
 * Represents a bus line
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 *
 */
@Root(name = "LINE")
public class Line extends Path {

	@Attribute
	private String code;
	@ElementList(name = "SREF", inline = true)
	private List<Sref> srefs;
	@Element(name = "LENGTH")
	private Length length;
	@ElementList(name = "STOP", inline = true)
	private List<Stop> stops;

	public List<Sref> getSrefs() {
		return srefs;
	}

	public void setSrefs(List<Sref> srefs) {
		this.srefs = srefs;
	}

	public Length getLength() {
		return length;
	}

	public void setLength(Length length) {
		this.length = length;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	public List<PathPoint> getPathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		PathPoint pp = null;

		for(Stop stop : stops) {
			
			pp = new PathPoint(stop.getLatitude(), stop.getLongitude());

			try {
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS86lalo(pp));
			} catch (CoordinateConversionFailed e) {
				e.printStackTrace();
			}
			pathPoints.add(pp);
		}
		return pathPoints;
	}

	@Override
	public PathType getPathType() {
		return PathType.LINE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
