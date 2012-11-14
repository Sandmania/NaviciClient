package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "Street")
public class Street {

	@Attribute
	private String language;
	@Attribute
	private Long locationId;
	private String value;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
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