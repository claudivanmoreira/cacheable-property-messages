package br.com.ceosites.cachedproperties.cache.test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.ceosites.cachedproperties.cache.PropertyCache;
import br.com.ceosites.cachedproperties.cache.PropertyCacheLoader;
import br.com.ceosites.cachedproperties.readers.FileBasedConfigurationReaderStrategy;

public class ProperyReaderStrategyTest {
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testCacheProperty() {
//		PropertiesConfiguration configuration = new PropertiesConfiguration();
//		try {
//			configuration.load("cached.properties");
//			FileBasedConfigurationReaderStrategy readerStrategy = new FileBasedConfigurationReaderStrategy(configuration);
//			PropertyCache cache = new PropertyCache(new PropertyCacheLoader(readerStrategy), 3L, 3L, 20L);
//			System.out.println(cache.getCachedProperty("text.color"));
//			System.out.println(cache.getCachedProperty("page.width"));
//			System.out.println(cache.getCachedProperty("pessoa.nome"));
//			Thread.sleep(4000);
//			System.out.println(cache.getCachedProperty("page.width"));
//			System.out.println(cache.getCachedProperty("pessoa.nome"));
//			Thread.sleep(2000);
//			System.out.println(cache.getCachedProperty("page.width"));
//			System.out.println(cache.getCachedProperty("pessoa.nome"));
//			
//		} catch (ConfigurationException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}

}
