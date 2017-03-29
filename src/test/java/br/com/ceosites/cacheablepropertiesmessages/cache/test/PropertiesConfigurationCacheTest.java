package br.com.ceosites.cacheablepropertiesmessages.cache.test;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import br.com.ceosites.cacheablepropertymessages.cache.PropertiesConfigurationCache;
import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;

public class PropertiesConfigurationCacheTest {

	private PropertiesReader propertiesReader;
	private PropertiesConfigurationCache propertiesCache;
	
	@Before
	public void setup() {
		propertiesReader = new PropertiesReader(null, new String[]{"cacheable-files.properties"});
		propertiesCache = new PropertiesConfigurationCache(propertiesReader);
	}

	@Test
	public void testPropertiesReader() {
		try {
			System.out.println(propertiesCache.get("text.color"));
			System.out.println(propertiesCache.get("page.width"));
			Thread.sleep(4000);
			System.out.println(propertiesCache.get("text.color"));
			System.out.println(propertiesCache.get("page.width"));
			System.out.println(propertiesCache.get("text.color2"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
