package fi.sandman.navici;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import fi.sandman.navici.dto.request.AjaxRequestObject;
import fi.sandman.navici.dto.request.routeRequest.Location;
import fi.sandman.navici.dto.request.routeRequest.Output;
import fi.sandman.navici.dto.request.routeRequest.RouteRequest;
import fi.sandman.navici.utils.XmlUtil;


public class RouteRequestDtoGeneratorTest extends TestCase {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public void testRouteRequestGeneration() {
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
		String generatedRequest = null;
		StringBuilder sb = new StringBuilder();
		sb.append("requestXml=<navici_request>");
		try {
			sb.append(XmlUtil.writeRequestXml(ajaxRequest));
		} catch (Exception e1) {
			
		}
		sb.append("</navici_request>");
		
		
		generatedRequest = sb.toString();
		
		LOG.debug(generatedRequest);
	}

}
