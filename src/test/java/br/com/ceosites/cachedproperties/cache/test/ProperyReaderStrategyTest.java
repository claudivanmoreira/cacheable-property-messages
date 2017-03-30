package br.com.ceosites.cachedproperties.cache.test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.ceosites.cachedproperties.cache.ConfigurationCache;
import br.com.ceosites.cachedproperties.cache.ConfigurationCacheLoader;
import br.com.ceosites.cachedproperties.readers.ProperyReaderStrategy;

public class ProperyReaderStrategyTest {
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testCacheProperty() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		try {
			configuration.load("cached.properties");
			ProperyReaderStrategy readerStrategy = new ProperyReaderStrategy(configuration);
			ConfigurationCache cache = new ConfigurationCache(new ConfigurationCacheLoader(readerStrategy), 3L, 3L, 20L);
			System.out.println(cache.getCachedKey("text.color"));
			System.out.println(cache.getCachedKey("page.width"));
			System.out.println(cache.getCachedKey("pessoa.nome"));
			Thread.sleep(4000);
			System.out.println(cache.getCachedKey("page.width"));
			System.out.println(cache.getCachedKey("pessoa.nome"));
			Thread.sleep(2000);
			System.out.println(cache.getCachedKey("page.width"));
			System.out.println(cache.getCachedKey("pessoa.nome"));
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
