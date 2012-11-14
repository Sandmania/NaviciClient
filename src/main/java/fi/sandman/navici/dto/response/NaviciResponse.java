package fi.sandman.navici.dto.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "navici_response")
public class NaviciResponse {
	
	@Element(name = "ajax_response_object")
	private AjaxResponseObject ajaxResponseObject;

	public AjaxResponseObject getAjaxResponseObject() {
		return ajaxResponseObject;
	}

	public void setAjaxResponseObject(AjaxResponseObject ajaxResponseObject) {
		this.ajaxResponseObject = ajaxResponseObject;
	}

}
