package fi.sandman.navici.dto.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.request.locationRequest.Request;
import fi.sandman.navici.dto.request.routeRequest.RouteRequest;

@Root(name = "ajax_request_object")
public class AjaxRequestObject extends NaviciRequest {

	public AjaxRequestObject(NaviciRequest requestDTO) {
		this.service = requestDTO.getServiceName();
		if (requestDTO instanceof RouteRequest) {
			this.setRouteRequestDTO((RouteRequest) requestDTO);
		} else if (requestDTO instanceof Request) {
			this.setRequest((Request)requestDTO);
		}
	}

	@Element(name = "Request", data = false, required = false)
	private Request request;
	@Element(name = "get_route", data = false, required = false)
	private RouteRequest routeRequestDTO;
	@Attribute(name = "object_id")
	private Long id;
	@Attribute
	private String service;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public RouteRequest getRouteRequestDTO() {
		return routeRequestDTO;
	}

	public void setRouteRequestDTO(RouteRequest routeRequestDTO) {
		this.routeRequestDTO = routeRequestDTO;
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
