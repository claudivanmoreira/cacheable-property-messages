package br.com.ceosites.cacheableconfiguration.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public class ConfigurationCache {
	

	private ConfigurationCacheLoader cacheLoader;
	private LoadingCache<String, String> cache;
	
	/**
	 * 
	 * @param cacheLoader
	 * @throws Exception
	 */
	public ConfigurationCache (ConfigurationCacheLoader cacheLoader) throws Exception {
		this.cacheLoader = cacheLoader;
		buildCache(cacheLoader,  null, null, null, null);
	}
	
	/**
	 * 
	 * @param cacheLoader
	 * @param timeoutExpireAfterAcess
	 * @param timeoutExpireAfterWrite
	 * @param cacheSize
	 * @throws Exception
	 */
	public ConfigurationCache (ConfigurationCacheLoader cacheLoader, Long timeoutExpireAfterAcess, Long timeoutExpireAfterWrite, Long cacheSize) throws Exception {
		this.cacheLoader = cacheLoader;
		buildCache(cacheLoader,  timeoutExpireAfterAcess, timeoutExpireAfterWrite, cacheSize, null);
	}
	
	/**
	 * 
	 * @param cacheLoader
	 * @param timeoutExpireAfterAcess
	 * @param timeoutExpireAfterWrite
	 * @param timeoutRefreshAfterWrite
	 * @param cacheSize
	 * @throws Exception
	 */
	public ConfigurationCache(ConfigurationCacheLoader cacheLoader, Long timeoutExpireAfterAcess, Long timeoutRefreshAfterWrite, Long cacheSize, Long timeoutExpireAfterWrite) throws Exception {
		this.cacheLoader = cacheLoader;
		buildCache(cacheLoader,  timeoutExpireAfterAcess,  timeoutExpireAfterWrite,  timeoutRefreshAfterWrite, cacheSize);
	}

	private void buildCache(ConfigurationCacheLoader cacheLoader, Long timeoutExpireAfterAcess, Long timeoutExpireAfterWrite, Long timeoutRefreshAfterWrite, Long cacheSize) throws Exception {
		cache = CacheBuilder.newBuilder()
				.recordStats()
				.refreshAfterWrite(timeoutRefreshAfterWrite == null ? CacheConstants.DEFAULT_REFRESH_TIMEOUT : timeoutRefreshAfterWrite, TimeUnit.SECONDS)
				.expireAfterAccess(timeoutExpireAfterAcess == null ? CacheConstants.DEFAULT_IDLE_TIME : timeoutExpireAfterAcess, TimeUnit.SECONDS)
				.expireAfterWrite(timeoutExpireAfterWrite == null ? CacheConstants.DEFAULT_TIME_TO_LIVE : timeoutExpireAfterWrite, TimeUnit.SECONDS)
				.maximumSize(cacheSize == null ? CacheConstants.DEFAULT_CACHE_SIZE : cacheSize)
				.build(cacheLoader);
		cache.putAll(cacheLoader.loadAll());
	}

	public ConfigurationCacheLoader getCacheLoader() {
		return cacheLoader;
	}

	public void setCacheLoader(ConfigurationCacheLoader cacheLoader) {
		this.cacheLoader = cacheLoader;
	}
	
	public ConcurrentMap<String, String> getCacheAsmap() {
		return cache.asMap();
	}
	
	public String getCachedKey(String key) {
		return cache.getUnchecked(key);
	}

}
