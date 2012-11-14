package fi.sandman.navici.dto;

import fi.sandman.navici.dto.response.routeRequest.Point;

/**
 * Defines POJOs that can be used in route request
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 *
 */
public interface SearchableLocation {
	
	public String getCity();
	public Integer getStreetNumber();
	public String getStreetName();
	public Point getPoint();
	

}
