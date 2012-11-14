package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "Place")
public class Place {

	@Attribute
	private String type;
	@Attribute(required = false)
	private String matchMethod;
	@Attribute(required = false)
	private String locationIds;
	@Attribute(required = false)
	private String locationId;
	
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMatchMethod() {
		return matchMethod;
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	@Text(required = false)
	public String getValue() {
		return value;
	}

	@Text(required = false)
	public void setValue(String value) {
		this.value = value;
	}

}
