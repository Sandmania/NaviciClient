package fi.sandman.navici.dto.request.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Output {

	@Attribute
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
