package cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;

public class MessagesCache {
	
	private static MessagesCache messagesCache = new MessagesCache();
	private static LoadingCache<String, String> cache;

	public static MessagesCache buildCache() throws ConfigurationException, ExecutionException {
		final PropertiesReader propertiesReader = new PropertiesReader(null, new String[]{"cacheable-files.properties"});
		CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				populateCache(propertiesReader);
				return cache.get(key);
			}
		};

		cache = CacheBuilder.newBuilder().maximumSize(10)
				.expireAfterWrite(3, TimeUnit.SECONDS).expireAfterAccess(3, TimeUnit.SECONDS)
				.build(cacheLoader);
		
		return messagesCache;
	}
	
	private static void populateCache(PropertiesReader propertiesReader) throws ConfigurationException, ExecutionException {
		PropertiesConfiguration configuration = propertiesReader.getAllProperties();
		cache.putAll(new MessagesMap(configuration));
	}
	
	public String getKey(String key) {
		return cache.get(key);
	}
	
	
	
	static class MessagesMap extends HashMap<String, String> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1136204699118094448L;

		public MessagesMap(PropertiesConfiguration configuration) {
			Iterator<String> it = configuration.getKeys();
			while (it.hasNext()) {
				String mapKey = it.next();
				put(mapKey, configuration.getString(mapKey));
			}
		}
		
	}
}
