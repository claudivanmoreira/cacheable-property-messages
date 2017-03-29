package br.com.ceosites.cacheablepropertymessages.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;

public class PropertiesConfigurationCache {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertiesConfigurationCache.class);
	private PropertiesReader propertiesReader;
	private LoadingCache<String, String> cache;
	
	public PropertiesConfigurationCache(PropertiesReader propertiesReader) {
		this.propertiesReader = propertiesReader;
		buildCache();
	}
	
	private void buildCache() {
		cache = CacheBuilder.newBuilder().recordStats().maximumSize(4)
				.expireAfterAccess(4, TimeUnit.SECONDS)
				.expireAfterWrite(4,  TimeUnit.SECONDS)
				.build(new CacheLoader<String, String>(){
			
			private PropertiesConfiguration configuration;
			
			@Override
			public String load(String key) throws Exception {
				System.out.println("Cahamando load....");
				return getMap().get(key);
			}			
		});
	}
	
	public String get(String key) throws Exception {
		System.out.println("Cahamando get....");
		if (cache.asMap().containsKey(key)) {
			return cache.getUnchecked(key);
		} else {
			cache.putAll(getMap());
			return cache.getUnchecked(key);
		}
		
	}
	
	public Map<String,String> getMap() throws ConfigurationException {
		System.out.println("Chamando getMap...");
		PropertiesConfiguration configuration = propertiesReader.getAllProperties();
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> it = configuration.getKeys();
		while (it.hasNext()) {
			String key = it.next();
			map.put(key, configuration.getString(key));
		}
		return map;
	}

}
