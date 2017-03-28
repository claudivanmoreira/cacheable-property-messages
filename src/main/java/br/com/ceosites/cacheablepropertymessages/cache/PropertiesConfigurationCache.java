package br.com.ceosites.cacheablepropertymessages.cache;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import br.com.ceosites.cacheablepropertymessages.io.PropertiesIterable;
import br.com.ceosites.cacheablepropertymessages.io.PropertiesReader;

public class PropertiesConfigurationCache {

	private static Logger LOGGER = LoggerFactory.getLogger(PropertiesConfigurationCache.class);
	private PropertiesConfigurationCacheLoader cacheLoader;
	private PropertiesReader propertiesReader;
	private PropertiesConfiguration configuration;
	private LoadingCache<String, String> cache;
	private final Long DEFAULT_CACHE_SIZE = 1200L;
	private final Long DEFAULT_CACHE_TIMEOUT_SECONDS = 3600L;
	private final Long DEFAULT_CACHE_TIME_TO_LIVE_SECONDS = 300L;
	private Long maximumSize;
	private Long timeOutSeconds;
	private Long timeToLiveSeconds;

	public PropertiesConfigurationCache(PropertiesConfigurationCacheLoader cacheLoader, Long maximumSize, Long timeToLiveSeconds, Long timeOutSeconds) {
		this.cacheLoader = cacheLoader;
		this.maximumSize = maximumSize == null ? DEFAULT_CACHE_SIZE : maximumSize;
		this.timeOutSeconds = timeOutSeconds == null ? DEFAULT_CACHE_TIMEOUT_SECONDS : timeOutSeconds;
		this.timeToLiveSeconds = timeToLiveSeconds == null ? DEFAULT_CACHE_TIME_TO_LIVE_SECONDS : timeToLiveSeconds;
		buildCache();
	}

	private void buildCache() {
		LOGGER.debug("Cache configurations received: cacheLoader=<{}>, maximumSize <{}>, timeToLiveSeconds <{}>, timeOutSeconds<{}>.", new Object[]{cacheLoader, maximumSize, timeToLiveSeconds, timeOutSeconds});
		this.cache = CacheBuilder.newBuilder().maximumSize(maximumSize).expireAfterAccess(timeToLiveSeconds, TimeUnit.SECONDS)
				.expireAfterWrite(timeOutSeconds, TimeUnit.SECONDS).build(cacheLoader);
	}

	public String getCachedProperty(String key) {
		try {
			if (configuration == null) {
				configuration = propertiesReader.getAllProperties();
			}
			return this.cache.getAll(new PropertiesIterable(configuration)).get(key);
		} catch (Exception e) {
			LOGGER.error("Fail on trying to get value for the key {} from cache. Error Message: {}", key, e.getMessage());
			return StringUtils.EMPTY;
		}
	}
	
	public ConcurrentMap<String, String> getCacheAsMap() {
		return cache.asMap();
	}

}
