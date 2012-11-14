package fi.sandman.navici.locationRequestTests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

import com.google.gson.Gson;

import fi.sandman.navici.client.City;
import fi.sandman.navici.client.NaviciClient;
import fi.sandman.navici.dto.request.AjaxRequestObject;
import fi.sandman.navici.dto.request.NaviciRequest;
import fi.sandman.navici.dto.request.locationRequest.Address;
import fi.sandman.navici.dto.request.locationRequest.LocationRequest;
import fi.sandman.navici.dto.request.locationRequest.Place;
import fi.sandman.navici.dto.request.locationRequest.Request;
import fi.sandman.navici.dto.response.NaviciResponse;
import fi.sandman.navici.dto.response.locationResponse.JsonLocationResponse;
import fi.sandman.navici.utils.XmlUtil;

public class LocationRequestTests extends TestCase {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private NaviciRequest createNewLocationRequest(String searchTerm) {
		Request request = new Request();
		request.setMethodName("LocationRequest");
		request.setRequestID(0l);
		request.setMaximumResponses(30);
		
		Address address = new Address();
		
		Place place = new Place();
		place.setType("Municipality");
		place.setMatchMethod("All");
		place.setLocationIds("179,180,277,500");
		
		address.setPlace(place);
		
		LocationRequest locReq = new LocationRequest();
		locReq.setFreeFormLocationName(searchTerm);
		locReq.setAddress(address);
		
		request.setLocationRequest(locReq);
		AjaxRequestObject ajaxRequestObject = new AjaxRequestObject(request);
		ajaxRequestObject.setId(4l);
		return ajaxRequestObject;
	}
	
	public void testLocationRequestGeneration() throws Exception {
		LOG.debug(XmlUtil.writeRequestXml(createNewLocationRequest("Palokanorsi")));
	}
	
	public void testParseLocationResponse() {
		NaviciResponse response = null;
		try {
			response = XmlUtil.parseResponseXml(convertStreamToString(ClassLoader
					.getSystemResourceAsStream("jyvaskyla/locationNotFoundResponse.xml")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertNotNull(response);
	}
	
	public void testGsonLocationResponseParser () {
		String jsonString = "{\"key\":\"keskustie 23\",\"data\":{\"locations\":[{\"category\":\"address\",\"x\":\"3434867.250000\",\"y\":\"6904655.000000\",\"name\":\"Keskustie\",\"city\":\"Jyv\u00e4skyl\u00e4\",\"number\":23}]},\"status\":0}";
		JsonLocationResponse jlr = new Gson().fromJson(jsonString, JsonLocationResponse.class);
		jlr.getStatus();
	}

	public void testSendLocationRequest() throws UnsupportedEncodingException, Exception {
		NaviciClient nc = NaviciClient.getInstance(City.JYVASKYLA);
//		nc.sendRequest(createNewLocationRequest("Palokanorsi"));
		nc.sendRequest("key=keskustie&language=fi&maxresults=30");
	}
	
	public String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
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
