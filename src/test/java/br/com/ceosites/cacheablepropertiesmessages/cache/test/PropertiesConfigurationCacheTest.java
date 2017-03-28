package br.com.ceosites.cacheablepropertiesmessages.cache.test;

import static org.hamcrest.CoreMatchers.not;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import br.com.ceosites.cacheablepropertymessages.cache.PropertiesConfigurationCache;
import br.com.ceosites.cacheablepropertymessages.cache.PropertiesConfigurationCacheLoader;
import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;
import cache.MessagesCache;

public class PropertiesConfigurationCacheTest {

	private PropertiesReader propertiesReader;
	private PropertiesConfigurationCacheLoader cacheLoader;
	private PropertiesConfigurationCache propertiesCache;
	
	@Before
	public void setup() {
//		propertiesReader = new PropertiesReader(null, new String[]{"cacheable-files.properties"});
//		cacheLoader = new PropertiesConfigurationCacheLoader(propertiesReader);
//		propertiesCache = new PropertiesConfigurationCache(cacheLoader, null, null, null);
	}

	@Test
	@Ignore
	public void testPropertiesReader() {
		try {
			not(propertiesReader.getAllProperties().isEmpty());
			not(propertiesReader.getAllProperties().isEmpty());
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testCacheLoader() {
		try {
			CoreMatchers.notNullValue().matches(cacheLoader.load("background.color"));
			CoreMatchers.notNullValue().matches(cacheLoader.load("page.width"));
			CoreMatchers.notNullValue().matches(cacheLoader.load("background.color"));
			CoreMatchers.notNullValue().matches(cacheLoader.load("page.width"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testBuildCache() {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				.maximumSize(5)
				.expireAfterAccess(5, TimeUnit.SECONDS)
				.expireAfterWrite(10, TimeUnit.SECONDS).build(cacheLoader);
		try {
			System.out.println(cache.get("page.width"));
			System.out.println(cache.get("page.width"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValueCachedProperty() throws InterruptedException {
		try {
			MessagesCache cache = MessagesCache.buildCache();
			System.out.println("Primeira execucao...");
			System.out.println(cache.getKey("page.width"));
			System.out.println(cache.getKey("background.color"));
			System.out.println("Segunda execucao...");
			System.out.println(cache.getKey("page.width"));
			System.out.println(cache.getKey("background.color"));
			Thread.sleep(3000);
			System.out.println("Terceira execucao...");
			System.out.println(cache.getKey("page.width"));
			System.out.println(cache.getKey("background.color"));
			System.out.println("Quarta execucao...");
			System.out.println(cache.getKey("page.width"));
			System.out.println(cache.getKey("background.color"));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}

}
