package fi.sandman.navici.dto.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.response.locationResponse.Response;
import fi.sandman.navici.dto.response.routeRequest.RouteResponse;

@Root(name = "ajax_response_object")
public class AjaxResponseObject {

	@Element(name = "route_response", required = false)
	private RouteResponse routeResponse;
	@Element(name = "Response", required = false)
	private Response locationResponse;

	public RouteResponse getRouteResponse() {
		return routeResponse;
	}

	public void setRouteResponse(RouteResponse routeResponse) {
		this.routeResponse = routeResponse;
	}

	public Response getLocationResponse() {
		return locationResponse;
	}

	public void setLocationResponse(Response locationResponse) {
		this.locationResponse = locationResponse;
	}
	
	
}
