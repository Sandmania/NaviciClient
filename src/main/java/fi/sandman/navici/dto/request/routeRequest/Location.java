package fi.sandman.navici.dto.request.routeRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/*
 <location order="0" x="3435231.25" y="6904685.5" name="Vapaudenkatu"
 number="53" city="Jyv�skyl�" />
 <location order="1" x="3433773" y="6902858.5" name="Korkeakoskentie"
 city="Jyv�skyl�" />
 */
@Root(name = "location")
public class Location {

	@Attribute
	private Integer order;
	@Attribute(name = "y", required = false)
	private Double y;
	@Attribute(name = "x", required = false)
	private Double x;
	@Attribute(required = false)
	private String name;
	@Attribute(required = false)
	private Integer number;
	@Attribute(required = false)
	private String city;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double latitude) {
		this.y = latitude;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double longitude) {
		this.x = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (name != null && name.length() > 0) {
			sb.append(name);
		}
		if (number != null && number > 0) {
			sb.append(" ");
			sb.append(number);
		}
		if (city != null && city.length() > 0) {
			sb.append(", ");
			sb.append(city);
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		if (((name == null && other.getName() == null) || (name != null && name.equals(other.getName())))
				&& ((number == null && other.getNumber() == null) || (number != null && number
						.equals(other.getNumber())))
				&& ((city == null && other.getCity() == null) || (city != null && city.equals(other.getCity())))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int multiplier = 23;
		int hashCode = 7;
		if (name != null) {
			hashCode = multiplier * hashCode + name.hashCode();
		}
		if (number != null) {
			hashCode = multiplier * hashCode + number.hashCode();
		}
		if (city != null) {
			hashCode = multiplier * hashCode + city.hashCode();
		}
		return hashCode;
	}
}
