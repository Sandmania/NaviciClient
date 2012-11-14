package fi.sandman.navici;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.sandman.navici.client.City;
import fi.sandman.navici.client.NaviciClient;

/**
 * Test for reading navici configs
 * 
 * @author sandman
 *
 */
public class ConfigReaderTest extends TestCase {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public void testGetAuthToken() {
		NaviciClient nc = NaviciClient.getInstance(City.JYVASKYLA);
		String authToken = nc.getAuthToken();
		LOG.debug(authToken);
		assertNotNull(authToken);
	}
}
