package fi.sandman.navici.dto.response.routeRequest;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class MTRXML {

	@ElementList(name = "ROUTE", inline = true)
	private List<Route> routes;

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}