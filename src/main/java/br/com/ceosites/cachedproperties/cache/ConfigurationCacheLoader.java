package br.com.ceosites.cachedproperties.cache;

import java.util.Map;

import com.google.common.cache.CacheLoader;

import br.com.ceosites.cachedproperties.readers.ReaderStrategy;

public class ConfigurationCacheLoader extends CacheLoader<String, String> {
	
	private ReaderStrategy readerStrategy;
	
	public ConfigurationCacheLoader(ReaderStrategy readerStrategy) {
		this.readerStrategy = readerStrategy;
	}

	@Override
	public String load(String key) throws Exception {
		return this.readerStrategy.getValue(key);
	}
	
	public Map<String, String> loadAll() throws Exception {
		return this.readerStrategy.getMapKeys();
	}
}
