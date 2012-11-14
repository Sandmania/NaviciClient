package fi.sandman.navici.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fi.sandman.navici.dto.response.routeRequest.Route;

/**
 * For displaying route response data as a summary
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public class RouteSummary {

	private String routeStart;
	private String routeEnd;
	private Double totalTime = 0d;
	private Double totalWalkDistance = 0d;
	private Route route;
	private List<String> pathSummaries;

	public String getRouteStart() {
		if (routeStart != null) {
			return routeStart.substring(0, 2) + ":"
					+ routeStart.substring(2, 4);
		}
		return routeStart;
	}

	public void setRouteStart(String routeStart) {
		this.routeStart = routeStart;
	}

	public String getRouteEnd() {
		if (routeEnd != null) {
			return routeEnd.substring(0, 2) + ":" + routeEnd.substring(2, 4);
		}
		return routeEnd;
	}

	public void setRouteEnd(String routeEnd) {
		this.routeEnd = routeEnd;
	}

	public String getTotalTimeDisplayFormat() {
		if (totalTime != null) {
			int t = new Long(Math.round(totalTime)).intValue();
			int hours = t / 60;
			int minutes = t % 60;
			if (hours < 1) {
				return minutes + " min";
			} else {
				return hours + " h " + minutes + " min";
			}
		}
		return "n/a";
	}

	public Double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

	public Double getTotalWalkDistance() {
		return totalWalkDistance;
	}

	public String getTotalWalkDistanceDisplayFormat() {
		DecimalFormat df = new DecimalFormat("#.#");
		if (totalWalkDistance != null) {
			return df.format(totalWalkDistance / 1000) + " km";
		}
		return "";
	}

	public void setTotalWalkDistance(Double totalWalkDistance) {
		this.totalWalkDistance = totalWalkDistance;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public List<String> getPathSummaries() {
		return pathSummaries;
	}

	public void setPathSummaries(List<String> pathSummaries) {
		this.pathSummaries = pathSummaries;
	}

	public void addPathSummary(String pathSummary) {
		if (pathSummaries == null) {
			pathSummaries = new ArrayList<String>();
		}
		pathSummaries.add(pathSummary);
	}

	public void addTotalTime(Double time) {
		totalTime += time;
	}

	public void addTotalWalkDistance(Double distance) {
		totalWalkDistance += distance;
	}
}
