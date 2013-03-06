package fi.sandman.navici.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fi.sandman.navici.NaviciConfig;
import fi.sandman.navici.dto.SearchableLocation;
import fi.sandman.navici.dto.request.AjaxRequestObject;
import fi.sandman.navici.dto.request.NaviciRequest;
import fi.sandman.navici.dto.request.locationRequest.Address;
import fi.sandman.navici.dto.request.locationRequest.LocationRequest;
import fi.sandman.navici.dto.request.locationRequest.Place;
import fi.sandman.navici.dto.request.locationRequest.Request;
import fi.sandman.navici.dto.request.routeRequest.Location;
import fi.sandman.navici.dto.request.routeRequest.Output;
import fi.sandman.navici.dto.request.routeRequest.RouteRequest;
import fi.sandman.navici.dto.response.NaviciResponse;
import fi.sandman.navici.dto.response.locationResponse.JsonLocationResponse;
import fi.sandman.navici.utils.XmlUtil;

/**
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public final class NaviciClient {

	private static City city;

	private static NaviciClient nc;
	private static final String SERVICE_URL = ".serviceUrl";

	public static City getCity() {
		return city;
	}

	public static NaviciClient getInstance() {
		if (nc == null) {
			nc = new NaviciClient();
		}
		return nc;
	}

	public static NaviciClient getInstance(City city) {
		if (nc == null) {
			nc = new NaviciClient(city);
		}
		// change city if needed
		if (!city.equals(NaviciClient.city)) {
			NaviciClient.city = city;
		}
		return nc;
	}

	public static void setCity(City city) {
		NaviciClient.city = city;
	}

	private final Logger log = LoggerFactory.getLogger(getClass());

	private Properties properties;

	/**
	 * Not meant to be instantiated
	 */
	private NaviciClient() {
		this(City.JYVASKYLA);
	}

	private NaviciClient(City city) {
		try {
			properties = getPropertiesFromClasspath();
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to load properties file", e);
			}
		}
		NaviciClient.city = city;
	}

	public Location generateLocationDTO(int order, SearchableLocation location) {
		Location loc = new Location();
		loc.setOrder(order);
		loc.setCity(location.getCity());
		loc.setName(location.getStreetName());
		loc.setNumber(location.getStreetNumber());
		if (location.getPoint() != null && location.getPoint().getPos() != null) {
			String[] split = location.getPoint().getPos().split("\\s+");
			if (split.length == 2) {
				loc.setX(Double.parseDouble(split[0]));
				loc.setY(Double.parseDouble(split[1]));
			}
		}
		return loc;
	}

	/**
	 * @deprecated NaviciServer doesn't use this anmore. Use
	 *             {@link NaviciClient#sendRequest(String)} instead. Deprecated
	 * @param searchTerm
	 * @return
	 */
	@Deprecated
	public AjaxRequestObject generateLocationRequest(String searchTerm) {
		Request request = new Request();
		request.setMethodName("LocationRequest");
		request.setRequestID(0l);
		request.setMaximumResponses(30);

		Address address = new Address();

		Place place = new Place();
		place.setType("Municipality");
		place.setMatchMethod("All");
		place.setLocationIds(properties.getProperty(city.getPropertiesName()
				+ ".locations"));

		address.setPlace(place);

		LocationRequest locReq = new LocationRequest();
		locReq.setFreeFormLocationName(searchTerm);
		locReq.setAddress(address);

		request.setLocationRequest(locReq);
		AjaxRequestObject ajaxRequestObject = new AjaxRequestObject(request);
		ajaxRequestObject.setId(4l);
		return ajaxRequestObject;
	}

	/**
	 * Generates a route request.
	 * 
	 * @param date
	 *            the day
	 * @param time
	 *            time of day, eg. "0705" for 7:05
	 * @param fromLoc
	 * @param toLoc
	 * @param timeDirection
	 *            "forward" for departure, "backward" for arrival
	 * @return
	 */
	public AjaxRequestObject generateRouteRequest(Date date, String time,
			Location fromLoc, Location toLoc, String timeDirection) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		RouteRequest route = new RouteRequest();

		route.setId(1l);
		route.setLanguage("fi");
		route.setTimeDirection(timeDirection);
		route.setDate(sdf.format(date));
		route.setTime(time);
		route.setWalkSpeed(70);
		route.setMaxWalk(1500);
		route.setRoutingMethod("default");
		route.setChangeMargin(3);
		route.setNumberRoutes(5);

		Output output = new Output();
		output.setType("gui_objects");

		route.addLocation(fromLoc);
		route.addLocation(toLoc);
		route.setOutput(output);

		AjaxRequestObject ajaxRequest = new AjaxRequestObject(route);
		ajaxRequest.setId(1l);

		return ajaxRequest;
	}

	/**
	 * Read auth token from config.js. See {@link NaviciConfig}
	 * 
	 * @return
	 */
	public String getAuthToken() {
		String authToken = null;
		String uri = properties.getProperty(city.getPropertiesName()
				+ SERVICE_URL);
		uri += properties.getProperty("configUrl");

		try {
			URL config = new URL(uri);
			URLConnection yc = config.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));

			String inputLine;
			NaviciConfig naviciConfig = null;
			while ((inputLine = in.readLine()) != null) {
				if (!inputLine.startsWith("var Config")) {
					continue;
				}
				int startIndex = inputLine.indexOf('{');
				int endIndex = inputLine.lastIndexOf('}');
				String jsonString;
				if (startIndex != -1 && endIndex != -1) {
					jsonString = inputLine.substring(startIndex, endIndex + 1);
					naviciConfig = new Gson().fromJson(jsonString,
							NaviciConfig.class);
					authToken = naviciConfig.getToken();
				}
			}
		} catch (MalformedURLException e) {
			log.debug("Failed to get auth token", e);
		} catch (IOException e) {
			log.debug("Failed to get auth token", e);
		}

		return authToken;
	}

	/**
	 * Executes the given {@link HttpPost} and returns the
	 * {@link BasicHttpResponse responses} {@link HttpEntity} as string.
	 * 
	 * 
	 * @param httpClient
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private String getHttpResponseEntityAsString(HttpPost httpPost)
			throws ClientProtocolException, IOException {
		String res = null;

		HttpClient httpclient = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) httpclient
				.execute(httpPost);

		res = EntityUtils.toString(httpResponse.getEntity());

		return res;

	}

	private Properties getPropertiesFromClasspath() throws IOException {
		String propFileName = "naviciClient.properties";
		Properties props = new Properties();
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName
					+ "' not found in the classpath");
		}

		props.load(inputStream);

		return props;
	}

	/**
	 * Creates and XML representation of the given request object and envelops
	 * it in <navici_request> -tag. This XML is then URL encoded (UTF-8) and
	 * that encoded string is appended to "requestXml=" -string.
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public NaviciResponse sendRequest(NaviciRequest request)
			throws UnsupportedEncodingException, Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("<navici_request>");
		sb.append(URLEncoder.encode(XmlUtil.writeRequestXml(request), "UTF-8"));
		sb.append("</navici_request>");
		String requestXml = "requestXml=" + sb.toString();
		String uri = properties.getProperty(city.getPropertiesName()
				+ SERVICE_URL)
				+ "ajaxRequest.php?token=";
		uri += getAuthToken();
		HttpPost httppost = new HttpPost(uri);
		String res = null;

		if (log.isDebugEnabled()) {
			log.debug(requestXml);
		}

		StringEntity se = new StringEntity(requestXml, HTTP.UTF_8);
		se.setContentType("text/xml");
		httppost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		httppost.setHeader("Origin",
				properties.getProperty(city.getPropertiesName() + SERVICE_URL));
		httppost.setHeader("Referer",
				properties.getProperty(city.getPropertiesName() + SERVICE_URL));
		httppost.setEntity(se);

		res = getHttpResponseEntityAsString(httppost);

		return XmlUtil.parseResponseXml(res);
	}

	/**
	 * Sends a plain request
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public JsonLocationResponse sendRequest(String request)
			throws ClientProtocolException, IOException {
		String res = null;

		String uri = properties.getProperty(city.getPropertiesName()
				+ SERVICE_URL)
				+ "geocode.php";
		HttpPost httppost = new HttpPost(uri);
		String requestString = "language=fi&maxresults=30&key="
				+ URLEncoder.encode(request, "UTF-8");
		StringEntity se = new StringEntity(requestString + "&token="
				+ getAuthToken(), HTTP.UTF_8);
		se.setContentType("text/xml");
		httppost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		httppost.setHeader("Origin",
				properties.getProperty(city.getPropertiesName() + SERVICE_URL));
		httppost.setHeader("Referer",
				properties.getProperty(city.getPropertiesName() + SERVICE_URL));
		httppost.setEntity(se);

		res = getHttpResponseEntityAsString(httppost);
		return new Gson().fromJson(res, JsonLocationResponse.class);
	}
}
