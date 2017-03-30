package br.com.ceosites.cachedproperties.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;

import br.com.ceosites.cachedproperties.readers.ReaderStrategy;

public class ConfigurationCacheLoader extends CacheLoader<String, String> {
	
	private Logger LOGGER = LoggerFactory.getLogger(ConfigurationCacheLoader.class);
	
	private ReaderStrategy readerStrategy;
	
	public ConfigurationCacheLoader(ReaderStrategy readerStrategy) {
		this.readerStrategy = readerStrategy;
	}

	@Override
	public String load(String key) throws Exception {
		LOGGER.debug("Load key {}", key);
		return this.readerStrategy.getValue(key);
	}
	
	public Map<String, String> loadAll() throws Exception {
		LOGGER.debug("Populando o cache");
		return this.readerStrategy.getMapKeys();
	}
}
