package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "route_response")
public class RouteResponse {

	@Element(name = "gui_objects")
	private GuiObjects guiObjects;

	public GuiObjects getGuiObjects() {
		return guiObjects;
	}

	public void setGuiObjects(GuiObjects guiObjects) {
		this.guiObjects = guiObjects;
	}

}



