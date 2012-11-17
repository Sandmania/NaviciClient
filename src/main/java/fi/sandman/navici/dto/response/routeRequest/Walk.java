package fi.sandman.navici.dto.response.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import fi.sandman.utils.coordinate.CoordinateConversionFailed;
import fi.sandman.utils.coordinate.CoordinateUtils;

@Root(name = "WALK")
public class Walk extends Path {

	@Element(name = "LENGTH")
	private Length length;
	@ElementList(name = "POINT", inline = true, required = false)
	// we can walk from stop to stop, so no end or start point
	private List<Point> points;
	@ElementList(name = "MAPLOC", inline = true, required = false)
	private List<Maploc> mapLocs;
	@ElementList(name = "STOP", inline = true, required = false)
	// we can walk from stop to stop so we might have two stops. Then again, we
	// can walk from point to point so possible no stops at all
	private List<Stop> stops;

	public Length getLength() {
		return length;
	}

	public void setLength(Length length) {
		this.length = length;
	}

	public List<Maploc> getMapLocs() {
		return mapLocs;
	}

	public void setMapLocs(List<Maploc> mapLocs) {
		this.mapLocs = mapLocs;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	@Override
	public List<PathPoint> getPathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		PathPoint pp;
		try {
			// ok, lets see what we've got here..
			// if POINT = null and stops.size == 2 we are walking from stop to stop
			if ((points == null || points.size() == 0) && stops != null && stops.size() == 2) {
				pp = new PathPoint(stops.get(0).getLatitude(), stops.get(0).getLongitude());
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));

				pathPoints.add(pp);

				pathPoints.addAll(getMapLocPathPoints());
				pp = new PathPoint(stops.get(1).getLatitude(), stops.get(1).getLongitude());
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));

				pathPoints.add(pp);

				return pathPoints;
			}
			
			// from point to point
			if ((stops == null || stops.size() == 0) && points != null && points.size() == 2) {
				pp = new PathPoint(points.get(0).getLatitude(), points.get(0).getLongitude());
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));
						
				pathPoints.add(pp);

				pathPoints.addAll(getMapLocPathPoints());
				
				pp = new PathPoint(points.get(1).getLatitude(), points.get(1).getLongitude());
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));
						
				pathPoints.add(pp);

				return pathPoints;
			}
			
			// ok, it wasn't that straight forward
			if (points != null && stops != null && stops.size() == 1 && points.size() == 1) {
				pp = new PathPoint(points.get(0).getLatitude(), points.get(0).getLongitude());
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));

				PathPoint stopPp = new PathPoint(stops.get(0).getLatitude(), stops.get(0).getLongitude());
				stopPp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(stopPp));
				if (points.get(0).getUid().equals("start")) { // starting point..
					pathPoints.add(pp);
					pathPoints.addAll(getMapLocPathPoints());
					pathPoints.add(stopPp);
					return pathPoints;
				} else if (points.get(0).getUid().equals("end")) { // ..or ze end
					pathPoints.add(stopPp);
					pathPoints.addAll(getMapLocPathPoints());
					pathPoints.add(pp);
					return pathPoints;
				}
			}
		} catch (CoordinateConversionFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if everything fails, lets just add the maplocs
		pathPoints.addAll(getMapLocPathPoints());
		return pathPoints;
	}

	private List<PathPoint> getMapLocPathPoints() {
		List<PathPoint> pathPoints = new ArrayList<PathPoint>();
		if(mapLocs == null) {
			return pathPoints;
		}
		PathPoint pp;
		for (Maploc ml : mapLocs) {
			pp = new PathPoint(ml.getLatitude(), ml.getLongitude());
			try {
				pp = new PathPoint(CoordinateUtils.convertKKJxyToWGS84lalo(pp));
			} catch (CoordinateConversionFailed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pathPoints.add(pp);
		}
		return pathPoints;
	}

	@Override
	public PathType getPathType() {
		return PathType.WALK;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Maploc getStartWaypoint() {
		Maploc maploc = new Maploc();

		// if POINT = null and stops.size == 2 we are walking from stop to stop
		if ((points == null || points.size() == 0) && stops != null && stops.size() == 2) {
			Stop stop = stops.get(0);
			maploc.setDeparture(stop.getDeparture());
			maploc.setLatitude(stop.getLatitude());
			maploc.setLongitude(stop.getLongitude());
			maploc.setName(stop.getName().get(0));
		}

		// from point to point
		if ((stops == null || stops.size() == 0) && points != null && points.size() == 2) {
			Point point = points.get(0);
			maploc.setDeparture(point.getDeparture());
			maploc.setLatitude(point.getLatitude());
			maploc.setLongitude(point.getLongitude());
			Name name = new Name();
			name.setVal(point.getPos());
			name.setLang(1L);
			maploc.setName(name);
		}

		// stop->point or point->stop
		if (points != null && stops != null && stops.size() == 1 && points.size() == 1) {
			if (points.get(0).getUid().equals("start")) { // starting point..
				Point point = points.get(0);
				maploc.setDeparture(point.getDeparture());
				maploc.setLatitude(point.getLatitude());
				maploc.setLongitude(point.getLongitude());
				Name name = new Name();
				name.setVal(point.getPos());
				name.setLang(1L);
				maploc.setName(name);
			} else if (points.get(0).getUid().equals("end")) { // ..or ze end
				Stop stop = stops.get(0);
				maploc.setDeparture(stop.getDeparture());
				maploc.setLatitude(stop.getLatitude());
				maploc.setLongitude(stop.getLongitude());
				maploc.setName(stop.getName().get(0));
			}
		}

		return maploc;
	}
}
