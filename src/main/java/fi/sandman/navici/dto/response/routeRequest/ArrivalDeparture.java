package fi.sandman.navici.dto.response.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class ArrivalDeparture {

	@Attribute
	private String date;
	@Attribute
	private String time;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
