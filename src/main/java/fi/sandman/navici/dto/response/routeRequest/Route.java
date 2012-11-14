package fi.sandman.navici.dto.response.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

@Root(name = "ROUTE")
public class Route {

	@Element(name = "LENGTH")
	private Length length;
	@ElementList(name = "SERVICE", inline = true, required = false)
	private List<Service> services;
	
	@ElementListUnion({
			@ElementList(entry = "WALK", inline = true, type = Walk.class),
			@ElementList(entry = "LINE", inline = true, type = Line.class),
			@ElementList(entry = "POINT", inline = true, type = Point.class) })
	private List<Path> path;

	public Length getLength() {
		return length;
	}

	public void setLength(Length length) {
		this.length = length;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<Path> getPath() {
		return path;
	}

	public void setPath(List<Path> path) {
		this.path = path;
	}
	
	public List<PathPoint> getRoutePathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		for(Path p : path) {
			pathPoints.addAll(p.getPathPoints());
		}
		return pathPoints;
	}

}
