package fi.sandman.navici.dto.response.locationResponse;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.SearchableLocation;

@Root(name = "LocationResponseList")
public class LocationResponse {

	@Attribute
	private int numberOfGeocodedAddresses;
	@Attribute
	private int status;
	@ElementList(name = "POI", inline = true, required = false)
	private List<Poi> pois;
	@ElementList(name = "GeocodedAddress", inline = true, required = false)
	private List<GeocodedAddress> geocodedAddresses;

	public int getNumberOfGeocodedAddresses() {
		return numberOfGeocodedAddresses;
	}

	public void setNumberOfGeocodedAddresses(int numberOfGeocodedAddresses) {
		this.numberOfGeocodedAddresses = numberOfGeocodedAddresses;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Poi> getPois() {
		return pois;
	}

	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}

	public List<GeocodedAddress> getGeocodedAddresses() {
		return geocodedAddresses;
	}

	public void setGeocodedAddresses(List<GeocodedAddress> geocodedAddresses) {
		this.geocodedAddresses = geocodedAddresses;
	}
	
	public List<SearchableLocation> getSearchableLocations() {
		List<SearchableLocation> searchableLocations = new ArrayList<SearchableLocation>();
		if(geocodedAddresses != null) {
			searchableLocations.addAll(geocodedAddresses);
		}
		if(pois != null) {
			searchableLocations.addAll(pois);
		}
		return searchableLocations;
	}

}
