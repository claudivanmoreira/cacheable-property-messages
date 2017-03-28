package br.com.ceosites.cacheablepropertymessages.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;

import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;

/**
 * 
 * @author claudivan.a.moreira
 *
 */
public class PropertiesConfigurationCacheLoader extends CacheLoader<String, String>{

	private Logger LOGGER = LoggerFactory.getLogger(PropertiesConfigurationCacheLoader.class);

	public PropertiesConfigurationCacheLoader(PropertiesReader propertiesReader) {
//		this.propertiesReader = propertiesReader;
	}

	@Override
	public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
		LOGGER.debug("Pushing all keys in cache...");
		Map<String, String> propsMap = new HashMap<String, String>();
		Iterator<? extends String> itKeys = keys.iterator();
		while(itKeys.hasNext()) {
			String key = itKeys.next();
//			LOGGER.debug("Add key {}={} in cache", key, configuration.getString(key));
//			propsMap.put(key, configuration.getString(key));
		}
		LOGGER.debug("Cache populated.");
		return propsMap;
	}

	@Override
	public String load(String key) throws Exception {
		
		throw new UnsupportedOperationException();
	}	
}
