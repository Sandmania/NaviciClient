package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "XTRA")
public class Xtra {

	@Attribute
	private String name;
	@Attribute
	private String val;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
