package fi.sandman.navici;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import fi.sandman.navici.client.City;
import fi.sandman.navici.client.NaviciClient;
import fi.sandman.navici.dto.request.AjaxRequestObject;
import fi.sandman.navici.dto.request.NaviciRequest;
import fi.sandman.navici.dto.request.routeRequest.Location;
import fi.sandman.navici.dto.request.routeRequest.Output;
import fi.sandman.navici.dto.request.routeRequest.RouteRequest;
import fi.sandman.navici.dto.response.NaviciResponse;
import fi.sandman.navici.dto.response.routeRequest.Length;
import fi.sandman.navici.dto.response.routeRequest.Line;
import fi.sandman.navici.dto.response.routeRequest.Name;
import fi.sandman.navici.dto.response.routeRequest.Path;
import fi.sandman.navici.dto.response.routeRequest.Point;
import fi.sandman.navici.dto.response.routeRequest.Route;
import fi.sandman.navici.dto.response.routeRequest.Service;
import fi.sandman.navici.dto.response.routeRequest.Walk;
import fi.sandman.navici.utils.RouteSummary;
import fi.sandman.navici.utils.XmlUtil;

public class RouteResponseParsingTest extends TestCase {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private AjaxRequestObject generateRouteRequest() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		RouteRequest route = new RouteRequest();

		route.setId(1l);
		route.setLanguage("fi");
		route.setTimeDirection("forward");
		route.setDate(sdf.format(Calendar.getInstance().getTime()));
		route.setTime("1500");
		route.setWalkSpeed(70);
		route.setMaxWalk(1500);
		route.setRoutingMethod("default");
		route.setChangeMargin(3);
		route.setNumberRoutes(5);

		Location from = new Location();
		Location to = new Location();
		from.setOrder(0);
		from.setName("Vapaudenkatu");
		from.setNumber(53);
		from.setCity("Jyväskylä");
		from.setX(3435231d);
		from.setY(6904685d);

		to.setOrder(1);
		to.setName("Korkeakoskentie");
		to.setCity("Jyväskylä");
		to.setX(3433773d);
		to.setY(6902858d);

		Output output = new Output();
		output.setType("gui_objects");

		route.addLocation(from);
		route.addLocation(to);
		route.setOutput(output);

		AjaxRequestObject ajaxRequest = new AjaxRequestObject(route);
		ajaxRequest.setId(1l);
		return ajaxRequest;
	}

	public void testRouteRequestAndResponse()
			throws UnsupportedEncodingException, Exception {
		NaviciClient client = NaviciClient.getInstance(City.JYVASKYLA);
		NaviciRequest request = generateRouteRequest();
		NaviciResponse response = client.sendRequest(request);
	}

	public void testRouteParsing() {
		NaviciResponse response = null;
		try {
			response = XmlUtil
					.parseResponseXml(convertStreamToString(ClassLoader
							.getSystemResourceAsStream("jyvaskyla/routeResponse.xml")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(response);
		assertNotNull(response.getAjaxResponseObject().getRouteResponse());

		List<Route> routes = response.getAjaxResponseObject()
				.getRouteResponse().getGuiObjects().getMtrxml().getRoutes();

		List<RouteSummary> routeSummaries = new ArrayList<RouteSummary>();

		RouteSummary rs = null;
		for (Route route : routes) {
			rs = new RouteSummary();
			for (Path path : route.getPath()) {
				if (path instanceof Point) { // get start and end times
					if (((Point) path).getUid().equals("start")) {
						rs.setRouteStart(((Point) path).getArrival().getTime());
					} else if (((Point) path).getUid().equals("end")) {
						rs.setRouteEnd(((Point) path).getDeparture().getTime());
					}
				} else if (path instanceof Walk) { // get walk length and ad
													// total time
					rs.addTotalTime(((Walk) path).getLength().getTime());
					rs.addTotalWalkDistance(((Walk) path).getLength().getDist());
					rs.addPathSummary("W");
				} else if (path instanceof Line) {
					rs.addTotalTime(((Line) path).getLength().getTime());
					rs.addPathSummary(((Line) path).getCode());
				}
			}
			routeSummaries.add(rs);
		}

		for (RouteSummary routeSummary : routeSummaries) {
			System.out
					.println(routeSummary.getRouteStart()
							+ " "
							+ routeSummary.getRouteEnd()
							+ " "
							+ Arrays.toString(routeSummary.getPathSummaries()
									.toArray()) + " "
							+ routeSummary.getTotalTime() + " " + routeSummary.getTotalWalkDistance());
		}

	}

	/**
	 * Just to see what kinda xml structure simple generates from the DTOs
	 * 
	 * @throws Exception
	 */
	public void testResponseCreation() throws Exception {

		Route route = new Route();

		Length length = new Length();
		length.setTime(24.201);
		length.setDist(7865.188);

		route.setLength(length);

		List<Service> services = new ArrayList<Service>();

		Service service = new Service();
		service.setId(113l);
		service.setCode("NT.37477");
		service.setCompany_id(1l);
		services.add(service);

		Service service2 = new Service();
		service2.setId(2400l);
		service2.setCode("DT.l");
		service2.setCompany_id(1l);
		services.add(service2);

		List<Name> names = new ArrayList<Name>();

		Name name1 = new Name();
		name1.setLang(1l);
		name1.setVal("val");
		names.add(name1);
		Name name2 = new Name();
		name2.setLang(2l);
		name2.setVal("val");
		names.add(name2);
		service.setNames(names);
		service2.setNames(names);
		
		route.setServices(services);
	}

	private String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

}
