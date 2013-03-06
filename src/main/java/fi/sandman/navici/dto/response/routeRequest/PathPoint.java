package fi.sandman.navici.dto.response.routeRequest;

import fi.sandman.utils.coordinate.CoordinatePoint;

/**
 * Describes a point on a navici path (e.g. bus route). Contains latitude and
 * longitude.
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public class PathPoint extends CoordinatePoint {

	private static final long serialVersionUID = 1L;

	public PathPoint(CoordinatePoint coordinatePoint) {
		super(coordinatePoint.getLatitude(), coordinatePoint.getLongitude());
	}

	public PathPoint(double latitude, double longitude) {
		super(latitude, longitude);
	}

}
