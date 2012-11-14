package fi.sandman.navici.dto.request.locationRequest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import fi.sandman.navici.dto.request.NaviciRequest;

/**

This class represents the third level: Request

<navici_request>
	<ajax_request_object object_id="4" service="Geocoding">
		<Request methodName="LocationRequest" requestID="0"
			maximumResponses="30">
			<LocationRequest>
				<FreeFormLocationName>Palokanorsi</FreeFormLocationName>
				<Address>
					<Place type="Municipality" matchMethod="All" locationIds="179,180,277,500"></Place>
				</Address>
			</LocationRequest>
		</Request>
	</ajax_request_object>
</navici_request>

 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 *
 */
@Root(name = "Request")
public class Request extends NaviciRequest {

	@Attribute
	private String methodName;
	@Attribute
	private Long requestID;
	@Attribute
	private int maximumResponses;
	@Element(name = "LocationRequest")
	private LocationRequest locationRequest;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Long getRequestID() {
		return requestID;
	}

	public void setRequestID(Long requestID) {
		this.requestID = requestID;
	}

	public int getMaximumResponses() {
		return maximumResponses;
	}

	public void setMaximumResponses(int maximumResponses) {
		this.maximumResponses = maximumResponses;
	}

	@Override
	public String getServiceName() {
		return "Geocoding";
	}

	public LocationRequest getLocationRequest() {
		return locationRequest;
	}

	public void setLocationRequest(LocationRequest locationRequest) {
		this.locationRequest = locationRequest;
	}

}
