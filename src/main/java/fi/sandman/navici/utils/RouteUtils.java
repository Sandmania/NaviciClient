package fi.sandman.navici.utils;

import java.util.ArrayList;
import java.util.List;

import fi.sandman.navici.dto.response.NaviciResponse;
import fi.sandman.navici.dto.response.routeRequest.Line;
import fi.sandman.navici.dto.response.routeRequest.Path;
import fi.sandman.navici.dto.response.routeRequest.Point;
import fi.sandman.navici.dto.response.routeRequest.Route;
import fi.sandman.navici.dto.response.routeRequest.Walk;

public class RouteUtils {

	public static List<RouteSummary> generateRouteSummaries(
			NaviciResponse response) {
		return generateRouteSummaries(response.getAjaxResponseObject()
				.getRouteResponse().getGuiObjects().getMtrxml().getRoutes());
	}

	/**
	 * Generates route summaries from given routes
	 * 
	 * @param routes
	 * @return
	 */
	public static List<RouteSummary> generateRouteSummaries(List<Route> routes) {
		List<RouteSummary> routeSummaries = new ArrayList<RouteSummary>();

		RouteSummary rs = null;
		for (Route route : routes) {
			rs = new RouteSummary();
			rs.setRoute(route);
			for (Path path : route.getPath()) {
				if (path instanceof Point) { // get start and end times
					if (((Point) path).getUid().equals("start")) {
						rs.setRouteStart(((Point) path).getArrival().getTime());
					} else if (((Point) path).getUid().equals("end")) {
						rs.setRouteEnd(((Point) path).getDeparture().getTime());
					}
				} else if (path instanceof Walk) { // get walk length and ad
													// total time
					rs.addTotalTime(((Walk) path).getLength().getTime());
					rs.addTotalWalkDistance(((Walk) path).getLength().getDist());
					rs.addPathSummary("W");
				} else if (path instanceof Line) {
					rs.addTotalTime(((Line) path).getLength().getTime());
					rs.addPathSummary(((Line) path).getCode());
				}
			}
			routeSummaries.add(rs);
		}
		return routeSummaries;
	}
}
