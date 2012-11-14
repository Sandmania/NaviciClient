package fi.sandman.navici.dto.request.routeRequest;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.request.NaviciRequest;

/*
 <navici_request>
 <ajax_request_object object_id="1" service="RouteRequests">
 <get_route id="1" language="fi" TimeDirection="forward"
 Date="20111012" Time="1547" WalkSpeed="70" MaxWalk="1500"
 RoutingMethod="default" ChangeMargin="3" NumberRoutes="5"
 ExcludedLines="">
 <output type="image_layer_objects" />
 <output type="gui_objects" />
 <location order="0" x="3435231.25" y="6904685.5" name="Vapaudenkatu"
 number="53" city="Jyv�skyl�" />
 <location order="1" x="3433773" y="6902858.5" name="Korkeakoskentie"
 city="Jyv�skyl�" />
 </get_route>
 </ajax_request_object>
 </navici_request>

 */
@Root(name = "get_route")
public class RouteRequest extends NaviciRequest {
	private static final String SERVICE_NAME = "RouteRequests";

	@Attribute
	private Long id;
	@Attribute
	private String language;
	@Attribute(name = "TimeDirection")
	private String timeDirection;
	@Attribute(name = "Date")
	private String date;
	@Attribute(name = "Time")
	private String time;
	@Attribute(name = "WalkSpeed")
	private Integer walkSpeed;
	@Attribute(name = "MaxWalk")
	private Integer maxWalk;
	@Attribute(name = "RoutingMethod")
	private String routingMethod;
	@Attribute(name = "ChangeMargin")
	private Integer changeMargin;
	@Attribute(name = "NumberRoutes")
	private Integer numberRoutes;
	@ElementList(name="location", inline=true)
	private List<Location> locations;
	@Element
	private Output output;

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTimeDirection() {
		return timeDirection;
	}

	public void setTimeDirection(String timeDirection) {
		this.timeDirection = timeDirection;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(Integer walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public Integer getMaxWalk() {
		return maxWalk;
	}

	public void setMaxWalk(Integer maxWalk) {
		this.maxWalk = maxWalk;
	}

	public String getRoutingMethod() {
		return routingMethod;
	}

	public void setRoutingMethod(String routingMethod) {
		this.routingMethod = routingMethod;
	}

	public Integer getChangeMargin() {
		return changeMargin;
	}

	public void setChangeMargin(Integer changeMargin) {
		this.changeMargin = changeMargin;
	}

	public Integer getNumberRoutes() {
		return numberRoutes;
	}

	public void setNumberRoutes(Integer numberRoutes) {
		this.numberRoutes = numberRoutes;
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
	
	public void addLocation(Location location) {
		if(this.locations == null) {
			this.locations = new ArrayList<Location>();
		}
		locations.add(location);
	}
}
