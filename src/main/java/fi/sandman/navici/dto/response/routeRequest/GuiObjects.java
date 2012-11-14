package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class GuiObjects {

	@Element(name = "MTRXML")
	private MTRXML mtrxml;

	public MTRXML getMtrxml() {
		return mtrxml;
	}

	public void setMtrxml(MTRXML mtrxml) {
		this.mtrxml = mtrxml;
	}

}