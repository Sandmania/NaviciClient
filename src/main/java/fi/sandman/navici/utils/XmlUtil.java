package fi.sandman.navici.utils;

import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.sandman.navici.dto.request.NaviciRequest;
import fi.sandman.navici.dto.response.NaviciResponse;

/**
 * SimpleXML powered xml reader / writer Creates a XML representation of the
 * request object and parses the response XML to a response object
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public class XmlUtil {

	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);
	
	private static Strategy strategy = new AnnotationStrategy();
	private static Serializer serial = new Persister(strategy);

	public static String writeRequestXml(NaviciRequest request)
			throws Exception {
		StringWriter writer = new StringWriter();
		serial.write(request, writer);
		if(log.isDebugEnabled()) {
			log.debug(writer.toString());
		}
		return writer.toString();
	}

	public static NaviciResponse parseResponseXml(String responseXml)
			throws Exception {
		if(log.isDebugEnabled()) {
			log.debug(responseXml);
		}
		NaviciResponse naviciResponse = new NaviciResponse();
		return serial.read(naviciResponse, responseXml, false);
	}

	public static NaviciResponse parseResponseXml(InputStream responseXml)
			throws Exception {
		NaviciResponse naviciResponse = new NaviciResponse();
		return serial.read(naviciResponse, responseXml, false);
	}
}
