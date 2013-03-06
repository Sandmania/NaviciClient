package fi.sandman.navici;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.http.client.methods.HttpPost;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

import fi.sandman.navici.client.City;
import fi.sandman.navici.client.NaviciClient;
import fi.sandman.navici.dto.request.AjaxRequestObject;
import fi.sandman.navici.dto.response.NaviciResponse;
import fi.sandman.navici.dto.response.locationResponse.JsonLocationResponse;
import fi.sandman.navici.dto.response.routeRequest.Line;
import fi.sandman.navici.dto.response.routeRequest.Path;
import fi.sandman.navici.dto.response.routeRequest.Point;
import fi.sandman.navici.dto.response.routeRequest.Route;
import fi.sandman.navici.dto.response.routeRequest.Walk;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NaviciClient.class })
public class LocationRequestOfflineTest {

	private static final String FROM_LOCATION_RESPONSE_JSON = "{\"key\":\"Helokantie 1\",\"data\":{\"locations\":[{\"category\":\"address\",\"x\":\"3431636.750000\",\"y\":\"6903327.000000\",\"name\":\"Helokantie\",\"city\":\"Jyv\u00e4skyl\u00e4\",\"number\":1}]},\"status\":0}";
	private static final String TO_LOCATION_RESPONSE_JSON = "{\"key\":\"Nevakatu 1\",\"data\":{\"locations\":[{\"category\":\"address\",\"x\":\"3437855.000000\",\"y\":\"6907888.500000\",\"name\":\"Nevakatu\",\"city\":\"Jyv\u00e4skyl\u00e4\",\"number\":1}]},\"status\":0}";

	/**
	 * A helper for creating a {@link AjaxRequestObject route request object}
	 * 
	 * @return
	 */
	private AjaxRequestObject generateAjaxRequestObject() {
		fi.sandman.navici.dto.request.routeRequest.Location fromLocation = generateLocationFromJsonString(FROM_LOCATION_RESPONSE_JSON);
		fi.sandman.navici.dto.request.routeRequest.Location toLocation = generateLocationFromJsonString(TO_LOCATION_RESPONSE_JSON);

		fromLocation.setOrder(0);
		toLocation.setOrder(1);

		NaviciClient naviciClient = NaviciClient.getInstance(City.JYVASKYLA);

		Date date = Calendar.getInstance().getTime();
		String time = "0845"; // Format HHmm, needs leading zero
		String timeDirection = "forward"; // Other possible value is "backward".
											// Use forward for departure time,
											// backward for arrival time
		AjaxRequestObject routeRequest = naviciClient.generateRouteRequest(
				date, time, fromLocation, toLocation, timeDirection);
		return routeRequest;
	}

	/**
	 * Helper for constructing a
	 * {@link fi.sandman.navici.dto.request.routeRequest.Location} from the
	 * given input string
	 * 
	 * @param string
	 * @return
	 */
	private fi.sandman.navici.dto.request.routeRequest.Location generateLocationFromJsonString(
			String string) {
		return new Gson().fromJson(string, JsonLocationResponse.class)
				.getData().getLocations().get(0);
	}

	private String getTotalWalkDistanceDisplayFormat(Double totalWalkDistance) {
		DecimalFormat df = new DecimalFormat("#.#");
		if (totalWalkDistance != null) {
			return df.format(totalWalkDistance / 1000) + " km";
		}
		return "";
	}

	/**
	 * Executes an offline location request, that returns a predetermined json
	 * response string.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSendLocationRequest() throws Exception {

		NaviciClient ncMock = PowerMock
				.createPartialMockAndInvokeDefaultConstructor(
						NaviciClient.class, "getHttpResponseEntityAsString");

		PowerMock.expectPrivate(ncMock, "getHttpResponseEntityAsString",
				EasyMock.isA(HttpPost.class)).andReturn(
				FROM_LOCATION_RESPONSE_JSON);

		PowerMock.replay(ncMock);
		JsonLocationResponse from = ncMock.sendRequest("Helokantie 1");
		PowerMock.verify(ncMock);

		Assert.assertNotNull(from);
		Assert.assertNotNull(from.getData());
		Assert.assertNotNull(from.getData().getLocations());
		Assert.assertEquals(from.getData().getLocations().size(), 1);

		fi.sandman.navici.dto.request.routeRequest.Location fromLocation = from
				.getData().getLocations().get(0);

		Assert.assertNotNull(fromLocation);
		Assert.assertEquals("Helokantie", fromLocation.getName());
		Assert.assertEquals(new Integer(1), fromLocation.getNumber());
		Assert.assertEquals("Jyväskylä", fromLocation.getCity());
		Assert.assertEquals(new Double(3431636.750000), fromLocation.getX());
		Assert.assertEquals(new Double(6903327.000000), fromLocation.getY());

	}

	@Test
	public void testSendRouteRequest() throws UnsupportedEncodingException,
			Exception {
		InputStream is = ClassLoader
				.getSystemResourceAsStream("jyvaskyla/helokantieToNevakatuRouteResponse.xml");

		String inputStreamString = new Scanner(is, "UTF-8").useDelimiter("\\A")
				.next();
		NaviciClient ncMock = PowerMock
				.createPartialMockAndInvokeDefaultConstructor(
						NaviciClient.class, "getHttpResponseEntityAsString");

		PowerMock.expectPrivate(ncMock, "getHttpResponseEntityAsString",
				EasyMock.isA(HttpPost.class)).andReturn(inputStreamString);

		AjaxRequestObject routeRequest = generateAjaxRequestObject();

		PowerMock.replay(ncMock);
		NaviciResponse response = ncMock.sendRequest(routeRequest);
		PowerMock.verify(ncMock);

		List<Route> foundRoutes = response.getAjaxResponseObject()
				.getRouteResponse().getGuiObjects().getMtrxml().getRoutes();

		Assert.assertEquals(5, foundRoutes.size());

		Route route1 = foundRoutes.get(0);

		// things to assert
		boolean foundStart = false;
		boolean foundEnd = false;
		boolean foundLine = false;
		int walkParts = 0;
		double totalWalkDistance = 0d;
		double totalWalkTime = 0d;

		Assert.assertEquals("1218", route1.getStartTimeOfRoute());
		Assert.assertEquals("0.2 km", getTotalWalkDistanceDisplayFormat(route1
				.getTotalWalkingDistance()));

		for (Path path : route1.getPath()) {
			// find start and end points of route and assert details for those
			if (path instanceof Point) {
				Point point = (Point) path;
				if (point.getUid().equals("start")) {
					Assert.assertEquals("1218", point.getArrival().getTime());
					foundStart = true;
					// This is the starting point of the route. The coordinates
					// should match Helokantie 1
				} else if (point.getUid().equals("end")) {
					Assert.assertEquals("1256", point.getDeparture().getTime());
					foundEnd = true;
					// This is the end point of the route. The coordinates
					// should match Nevakatu 1
				}
				// find walkin parts of route and assert details for those
			} else if (path instanceof Walk) {
				Walk walk = (Walk) path;
				totalWalkDistance += walk.getLength().getDist();
				totalWalkTime += walk.getLength().getTime();
				walkParts++;
			} else if (path instanceof Line) {
				Line line = (Line) path;
				foundLine = true;
				Assert.assertEquals("12", line.getCode());
			}

		}

		Assert.assertEquals(2, walkParts);

		Assert.assertTrue(foundStart);
		Assert.assertTrue(foundEnd);
		Assert.assertTrue(foundLine);
	}
}
