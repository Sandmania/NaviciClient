package fi.sandman.navici.dto.response.routeRequest;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.DTOObject;

@Root(name = "SERVICE")
public class Service implements DTOObject {

	@Attribute
	private Long id;
	@Attribute
	private String code;
	@Attribute
	private Long company_id;
	@ElementList(name = "NAME", inline = true)
	private List<Name> names;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}

	public List<Name> getNames() {
		return names;
	}

	public void setNames(List<Name> names) {
		this.names = names;
	}

}
