package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.DTOObject;

@Root(name = "NAME")
public class Name implements DTOObject {

	@Attribute
	private Long lang;
	@Attribute
	private String val;

	public Long getLang() {
		return lang;
	}

	public void setLang(Long lang) {
		this.lang = lang;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
