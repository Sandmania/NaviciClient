package fi.sandman.navici.dto.response.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

/**
 * Simplexml class for describing an entire route to take. This includes start
 * and end {@link Point points} of the route, the {@link Walk walking} parts of
 * the route and the actual {@link Line busline(s)} needed to take.
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
@Root(name = "ROUTE")
public class Route {

	@Element(name = "LENGTH")
	private Length length;
	@ElementListUnion({
			@ElementList(entry = "WALK", inline = true, type = Walk.class),
			@ElementList(entry = "LINE", inline = true, type = Line.class),
			@ElementList(entry = "POINT", inline = true, type = Point.class) })
	private List<Path> path;

	@ElementList(name = "SERVICE", inline = true, required = false)
	private List<Service> services;

	/**
	 * Finds the ending {@link Point} of the {@link Route} and returns the
	 * departure time of that point. This is the time when user should arrive in
	 * his / hers destination.
	 * 
	 * And yes, I know this sounds counter intuitive, but that's how it seems to
	 * be in the navici end.
	 * 
	 * @return
	 */
	public String getEndTimeOfRoute() {
		String endTime = "";
		for (Path p : path) {
			if (p instanceof Point) {
				Point point = (Point) p;
				if (point.getUid().equals("end")) {
					endTime = point.getDeparture().getTime();
				}
			}
		}
		return endTime;
	}

	public Length getLength() {
		return length;
	}

	public List<Path> getPath() {
		return path;
	}

	/**
	 * Returns all the {@link PathPoint path points / coordinate points} in this
	 * route.
	 * 
	 * @return
	 */
	public List<PathPoint> getRoutePathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		for (Path p : path) {
			pathPoints.addAll(p.getPathPoints());
		}
		return pathPoints;
	}

	public List<Service> getServices() {
		return services;
	}

	/**
	 * Finds the starting {@link Point} of the {@link Route} and returns the
	 * arrival time of that point. This is the time when user should start his
	 * journey in order to make it in time.
	 * 
	 * @return
	 */
	public String getStartTimeOfRoute() {
		String startTime = null;
		for (Path p : path) {
			if (p instanceof Point) {
				Point point = (Point) p;
				if (point.getUid().equals("start")) {
					startTime = point.getArrival().getTime();
				}
			}
		}
		return startTime;
	}

	/**
	 * Returns the total distance needed to walk in this route.
	 * 
	 * @return
	 */
	public Double getTotalWalkingDistance() {
		Double totalWalkingDistance = 0D;
		for (Path p : path) {
			if (p instanceof Walk) {
				Walk walk = (Walk) p;
				totalWalkingDistance += walk.getLength().getDist();
			}
		}
		return totalWalkingDistance;
	}

	public void setLength(Length length) {
		this.length = length;
	}

	public void setPath(List<Path> path) {
		this.path = path;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}
