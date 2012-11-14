package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.DTOObject;

@Root(name = "LENGTH")
public class Length implements DTOObject {
	
	@Attribute
	private Double time;
	@Attribute
	private Double dist;
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	public Double getDist() {
		return dist;
	}
	public void setDist(Double dist) {
		this.dist = dist;
	}

	
}
